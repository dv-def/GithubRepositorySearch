package ru.dvn.githubrepositorysearch.domain

interface Repository {
    suspend fun search(query: String): SearchResponse
}