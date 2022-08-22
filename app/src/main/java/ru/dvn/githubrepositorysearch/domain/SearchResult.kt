package ru.dvn.githubrepositorysearch.domain

data class SearchResult(
    val name: String?,
    val fullName: String?,
    val description: String?,
    val language: String?,
    val visibility: String?
)