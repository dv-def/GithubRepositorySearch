package ru.dvn.githubrepositorysearch.presentation

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.dvn.githubrepositorysearch.di.Di
import ru.dvn.githubrepositorysearch.domain.Repository
import ru.dvn.githubrepositorysearch.domain.SearchResponse

class SearchViewModel(
    private val repository: Repository,
): ViewModel() {
    private val liveData = MutableLiveData<SearchState>()

    fun getLiveData(): LiveData<SearchState> = liveData

    fun search(query: String) {
        liveData.postValue(SearchState.Loading)

        viewModelScope.launch {
            val result = repository.search(query)
            if (result.items != null) {
                liveData.postValue(SearchState.Success(data = result))
            } else {
                liveData.postValue(SearchState.Error(Throwable("Items are null")))
            }
        }
    }

    sealed class SearchState {
        data class Success(val data: SearchResponse): SearchState()
        data class Error(val throwable: Throwable): SearchState()
        object Loading: SearchState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                    return SearchViewModel(Di.repository) as T
                }

                throw IllegalStateException("Data types are different")
            }
        }
    }

}