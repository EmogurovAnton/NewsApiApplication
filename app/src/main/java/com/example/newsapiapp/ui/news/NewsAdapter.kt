package com.example.newsapiapp.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapiapp.R
import com.example.newsapiapp.data.model.NewsPost
import com.example.newsapiapp.databinding.ItemNewsPostBinding

class NewsAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<NewsPost, NewsAdapter.NewsViewHolder>(NEWS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class NewsViewHolder(private val binding: ItemNewsPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(newsPost: NewsPost) {
            binding.apply {
                Glide.with(imageNewsPost)
                    .load(newsPost.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageNewsPost)

                textTitle.text = newsPost.title
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(newsArticle: NewsPost)
    }

    companion object {
        val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<NewsPost>() {
            override fun areItemsTheSame(oldItem: NewsPost, newItem: NewsPost) =
                oldItem.source.id == newItem.source.id

            override fun areContentsTheSame(oldItem: NewsPost, newItem: NewsPost) =
                oldItem == newItem
        }
    }
}