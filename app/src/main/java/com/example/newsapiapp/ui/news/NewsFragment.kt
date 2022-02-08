package com.example.newsapiapp.ui.news

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.newsapiapp.R
import com.example.newsapiapp.data.model.NewsPost
import com.example.newsapiapp.databinding.FragmentsNewsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragments_news) {
    private lateinit var binding: FragmentsNewsBinding
    private val viewModel by viewModels<NewsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentsNewsBinding.bind(view)

        val adapter = NewsAdapter(object : NewsAdapter.OnItemClickListener {
            override fun onItemClick(newsArticle: NewsPost) {
                val action = NewsFragmentDirections.actionNewsFragmentToNewsDetails(newsArticle)
                findNavController().navigate(action)
            }
        })

        binding.apply {
            newsRecyclerView.adapter = adapter
            newsRecyclerView.setHasFixedSize(true)
            newsButtonRetry.setOnClickListener {
                adapter.retry()
            }

            adapter.addLoadStateListener { loadState ->
                newsProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                newsRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                newsRecyclerView.itemAnimator = null
                newsTextNoResults.isVisible = loadState.source.refresh is LoadState.Error
                newsButtonRetry.isVisible = loadState.source.refresh is LoadState.Error
            }
        }

        viewModel.news.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_news, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.newsRecyclerView.scrollToPosition(0)
                    viewModel.searchNews(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true
        })
    }
}
