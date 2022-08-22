package ru.dvn.githubrepositorysearch.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.dvn.githubrepositorysearch.databinding.ItemRepositoryBinding
import ru.dvn.githubrepositorysearch.domain.SearchResult

class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {
    private val repos = mutableListOf<SearchResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bindItem(repos[position])
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun updateData(result: List<SearchResult>) {
        repos.clear()
        repos.addAll(result)
        notifyDataSetChanged()
    }
}