package com.sonu.imagesearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sonu.imagesearchapp.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

/*
    Paging library takes care of calling load function in coroutine we don't call
    api directly
 */

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {

            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else UNSPLASH_STARTING_PAGE_INDEX - 1,
                nextKey = if (position == photos.size - 1) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)

        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? = null
}