package com.bronski.android.movieslistappselect.ui.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bronski.android.movieslistappselect.core.api.data.Movie
import com.bronski.android.movieslistappselect.ui.movies.model.source.internet.IMoviesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepoImpl: IMoviesRepo,
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies: StateFlow<PagingData<Movie>> get() = _movies

    init {
        getMoviesAll()
    }

    private fun getMoviesAll() {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepoImpl.getMoviesAllResult().collectLatest {
                _movies.value = it
            }
        }
    }

    fun swipeToUpdate() {
        _movies.value = PagingData.empty()
        getMoviesAll()
    }
}