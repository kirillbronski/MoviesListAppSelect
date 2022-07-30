package com.bronski.android.movieslistappselect.core.api

import com.bronski.android.movieslistappselect.core.api.data.MovieReviewsResponse
import com.bronski.android.movieslistappselect.core.utils.Constants.ALL_JSON
import com.bronski.android.movieslistappselect.core.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieReviewsAPI {

    @GET(ALL_JSON)
    suspend fun getMoviesAllResult(
        @Query("offset") offset: Int,
        @Query("api-key") apiKey: String = API_KEY,
    ): MovieReviewsResponse

}