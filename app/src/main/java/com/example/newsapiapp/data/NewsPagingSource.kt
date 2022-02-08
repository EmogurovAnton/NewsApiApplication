package com.example.newsapiapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapiapp.api.NewsApi
import com.example.newsapiapp.data.model.NewsPost
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val query: String
) : PagingSource<Int, NewsPost>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsPost> {
        val page = params.key ?: NEWS_STARTING_PAGE_INDEX
        val pageSize = params.loadSize

        return try {
            val response = newsApi.getNews(query, page, pageSize)
            val news = response.articles

            val prevKey = if (page == NEWS_STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (news.isEmpty()) null else page + 1

            LoadResult.Page(
                data = news,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NewsPost>): Int? {
        TODO("Not yet implemented")
    }
}