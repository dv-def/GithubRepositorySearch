package ru.dvn.githubrepositorysearch.data

import ru.dvn.githubrepositorysearch.domain.Repository
import ru.dvn.githubrepositorysearch.domain.SearchResponse
import ru.dvn.githubrepositorysearch.utils.toSearchResponse

class RepositoryImpl(
    private val api: SearchApi
) : Repository {

    override suspend fun search(query: String): SearchResponse {
        return api.search(query).toSearchResponse()
    }
}