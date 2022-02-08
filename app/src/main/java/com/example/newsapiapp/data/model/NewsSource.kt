package com.example.newsapiapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsSource(
    val id: String?
) : Parcelable