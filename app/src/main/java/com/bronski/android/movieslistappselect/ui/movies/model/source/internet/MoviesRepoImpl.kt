package com.bronski.android.movieslistappselect.ui.movies.model.source.internet

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bronski.android.movieslistappselect.core.api.MovieReviewsAPI
import com.bronski.android.movieslistappselect.core.api.data.Movie
import com.bronski.android.movieslistappselect.core.utils.Constants.PAGE_SIZE
import com.bronski.android.movieslistappselect.ui.movies.model.source.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val movieReviewsAPI: MovieReviewsAPI,
) : IMoviesRepo {

    override suspend fun getMoviesAllResult(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(movieReviewsAPI = movieReviewsAPI) }
        ).flow
    }

}
