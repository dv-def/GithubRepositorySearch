package ru.dvn.githubrepositorysearch.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.dvn.githubrepositorysearch.databinding.ActivityMainBinding
import ru.dvn.githubrepositorysearch.di.Di
import ru.dvn.githubrepositorysearch.domain.SearchResponse

class MainActivity : AppCompatActivity(), SearchView {
    private lateinit var binding: ActivityMainBinding

    private val adapter = SearchResultAdapter()
    private val presenter = SearchPresenter(Di.repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.rvRepositories.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }

        presenter.attach(this)

        binding.svSearch.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showData(data: SearchResponse) {
        binding.tvTotalCount.text = data.totalCount.toString()
        adapter.updateData(data.items)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}