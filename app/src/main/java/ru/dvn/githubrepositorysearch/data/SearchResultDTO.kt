package ru.dvn.githubrepositorysearch.data

import com.google.gson.annotations.SerializedName

data class SearchResultDTO(
    @SerializedName("name")
    val name: String?,

    @SerializedName("full_name")
    val fullName: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("visibility")
    val visibility: String?
)