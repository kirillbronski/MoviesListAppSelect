package com.bronski.android.movieslistappselect.core.utils

import com.bronski.android.movieslistappselect.BuildConfig

object Constants {
    const val BASE_URL = "https://api.nytimes.com"
    const val ALL_JSON = "/svc/movies/v2/reviews/all.json"
    const val API_KEY = BuildConfig.API_KEY
    const val PAGE_SIZE = 1
    const val FIRST_INDEX = 0
    const val OFFSET_STEP = 20
}