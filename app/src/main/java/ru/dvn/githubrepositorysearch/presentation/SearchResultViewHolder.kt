package ru.dvn.githubrepositorysearch.presentation

import androidx.recyclerview.widget.RecyclerView
import ru.dvn.githubrepositorysearch.databinding.ItemRepositoryBinding
import ru.dvn.githubrepositorysearch.domain.SearchResult

class SearchResultViewHolder(
    private val binding: ItemRepositoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(searchResult: SearchResult) {
        with(binding) {
            searchResult.name?.let { itemName.text = it }
            searchResult.fullName?.let { itemFullName.text = it }
            searchResult.description?.let { itemDescription.text = it }
            searchResult.visibility?.let { itemVisibility.text = it }
            searchResult.language?.let { itemLanguage.text = it }
        }
    }
}