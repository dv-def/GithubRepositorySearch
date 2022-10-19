package ru.dvn.githubrepositorysearch.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dvn.githubrepositorysearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: SearchViewModel by viewModels { SearchViewModel.Factory }
    private val adapter = SearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        viewModel.getLiveData().observe(this) { state ->
            parseState(state)
        }

        binding.rvRepositories.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        binding.svSearch.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.search(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun parseState(state: SearchViewModel.SearchState) {
        when(state) {
            is SearchViewModel.SearchState.Loading -> {
                with(binding) {
                    resultGroup.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
            }

            is SearchViewModel.SearchState.Error -> {
                with(binding) {
                    progressBar.visibility = View.GONE
                    resultGroup.visibility = View.VISIBLE
                }

                Toast.makeText(this, state.throwable.message, Toast.LENGTH_SHORT).show()
            }

            is SearchViewModel.SearchState.Success -> {
                with(binding) {
                    progressBar.visibility = View.GONE
                    resultGroup.visibility = View.VISIBLE
                    tvTotalCount.text = state.data.totalCount.toString()
                }

                state.data.items?.let { list ->
                    adapter.updateData(list)
                }
            }
        }
    }
}