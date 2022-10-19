package ru.dvn.githubrepositorysearch.utils

import ru.dvn.githubrepositorysearch.data.SearchResponseDTO
import ru.dvn.githubrepositorysearch.data.SearchResultDTO
import ru.dvn.githubrepositorysearch.domain.SearchResponse
import ru.dvn.githubrepositorysearch.domain.SearchResult

fun SearchResponseDTO.toSearchResponse(): SearchResponse {
    return SearchResponse(
        totalCount = this.totalCount,
        items = this.items?.map { dto -> dto.toSearchResult() }
    )
}

private fun SearchResultDTO.toSearchResult(): SearchResult {
    return SearchResult(
        name = this.name,
        fullName = this.fullName,
        description = this.description,
        language = this.language,
        visibility = this.visibility
    )
}