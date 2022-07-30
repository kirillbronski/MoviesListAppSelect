package com.bronski.android.movieslistappselect.core.api.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("display_title") val displayTitle: String,
    @SerializedName("summary_short") val summaryShort: String,
    @SerializedName("publication_date") val publicationDate: String,
    val multimedia: Multimedia,
)
