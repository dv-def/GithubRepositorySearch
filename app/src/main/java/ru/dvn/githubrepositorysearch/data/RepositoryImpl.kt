package ru.dvn.githubrepositorysearch.data

import retrofit2.Callback
import ru.dvn.githubrepositorysearch.domain.Repository

class RepositoryImpl(
    private val api: SearchApi
) : Repository {

    override fun search(query: String, callback: Callback<SearchResponseDTO>) {
        api.search(query).enqueue(callback)
    }
}