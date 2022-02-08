package com.example.newsapiapp.ui.details

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsapiapp.R
import com.example.newsapiapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {
    private lateinit var binding: FragmentNewsDetailsBinding
    private val args by navArgs<NewsDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsDetailsBinding.bind(view)

        val article = args.newsArticle
        binding.apply {
            Glide.with(this@NewsDetailsFragment)
                .load(article.urlToImage)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        detailsProgressBar.isVisible = false

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        detailsProgressBar.isVisible = false
                        detailsTextAuthor.isVisible = true
                        detailsTextTitle.isVisible = true
                        detailsTextDescription.isVisible = true
                        detailsTextLink.isVisible = true

                        return false
                    }
                }).into(detailsImageArticle)

            detailsTextAuthor.text = article.correctAuthor
            detailsTextTitle.text = article.title
            detailsTextDescription.text = article.description

            val uri = Uri.parse(article.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            detailsTextLink.setOnClickListener {
                context?.startActivity(intent)
            }
        }
    }
}