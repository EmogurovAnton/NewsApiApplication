package com.example.newsapiapp.api

import com.example.newsapiapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    companion object{
        const val BASE_URL = "https://newsapi.org/v2/"
        private const val API_KEY = BuildConfig.NEWS_ACCESS_KEY
    }

    @Headers("X-Api-key: $API_KEY ")
    @GET("everything")
    suspend fun getNews(
        @Query("q") q:String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ) : NewsResponse

}