package ru.dvn.githubrepositorysearch.data

import com.google.gson.annotations.SerializedName

data class SearchResponseDTO(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items: List<SearchResultDTO>
)