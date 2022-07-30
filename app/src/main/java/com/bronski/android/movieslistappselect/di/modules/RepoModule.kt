package com.bronski.android.movieslistappselect.di.modules

import com.bronski.android.movieslistappselect.core.api.MovieReviewsAPI
import com.bronski.android.movieslistappselect.ui.movies.model.source.internet.IMoviesRepo
import com.bronski.android.movieslistappselect.ui.movies.model.source.internet.MoviesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideMovieRepo(movieReviewsAPI: MovieReviewsAPI): IMoviesRepo {
        return MoviesRepoImpl(movieReviewsAPI)
    }

}