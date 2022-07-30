package com.bronski.android.movieslistappselect.ui.movies.model.source.internet

import androidx.paging.PagingData
import com.bronski.android.movieslistappselect.core.api.data.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesRepo {

    suspend fun getMoviesAllResult(): Flow<PagingData<Movie>>

}
