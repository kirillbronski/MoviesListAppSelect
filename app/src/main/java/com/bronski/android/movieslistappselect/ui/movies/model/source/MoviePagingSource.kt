package com.bronski.android.movieslistappselect.ui.movies.model.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bronski.android.movieslistappselect.core.api.MovieReviewsAPI
import com.bronski.android.movieslistappselect.core.api.data.Movie
import com.bronski.android.movieslistappselect.core.utils.Constants.FIRST_INDEX
import com.bronski.android.movieslistappselect.core.utils.Constants.OFFSET_STEP

class MoviePagingSource(
    private val movieReviewsAPI: MovieReviewsAPI,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextKey: Int = params.key ?: FIRST_INDEX

        return try {
            val responseApi = movieReviewsAPI.getMoviesAllResult(nextKey)
            val prevKey = if (nextKey == 0) null else nextKey - OFFSET_STEP
            return LoadResult.Page(
                data = responseApi.results,
                prevKey = prevKey,
                nextKey = nextKey.plus(OFFSET_STEP)
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }
}