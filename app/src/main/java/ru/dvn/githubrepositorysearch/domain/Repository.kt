package ru.dvn.githubrepositorysearch.domain

import retrofit2.Callback
import ru.dvn.githubrepositorysearch.data.SearchResponseDTO

interface Repository {
    fun search(query: String, callback: Callback<SearchResponseDTO>)
}