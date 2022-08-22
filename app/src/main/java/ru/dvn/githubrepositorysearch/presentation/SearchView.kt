package ru.dvn.githubrepositorysearch.presentation

import ru.dvn.githubrepositorysearch.domain.SearchResponse

interface SearchView {
    fun showData(data: SearchResponse)
    fun showError(message: String)
}