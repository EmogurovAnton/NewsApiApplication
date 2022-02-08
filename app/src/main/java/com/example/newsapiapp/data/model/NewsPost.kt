package com.example.newsapiapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsPost(
    val source: NewsSource,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
) : Parcelable {
    val correctAuthor: String
        get() = if (author.contains("(Unknown)")) {
            "Unknown"
        } else author
}