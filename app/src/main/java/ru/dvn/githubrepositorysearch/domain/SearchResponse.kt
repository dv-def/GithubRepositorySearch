package ru.dvn.githubrepositorysearch.domain

data class SearchResponse(
    val totalCount: Int,
    val items: List<SearchResult>?
)