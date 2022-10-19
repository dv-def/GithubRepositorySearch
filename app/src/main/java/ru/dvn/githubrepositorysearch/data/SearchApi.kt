package ru.dvn.githubrepositorysearch.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/repositories")
    suspend fun search(@Query("q") query: String): SearchResponseDTO
}