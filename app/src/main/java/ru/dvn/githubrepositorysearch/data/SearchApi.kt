package ru.dvn.githubrepositorysearch.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/repositories")
    fun search(@Query("q") query: String): Call<SearchResponseDTO>
}