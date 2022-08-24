package ru.dvn.githubrepositorysearch.presentation

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dvn.githubrepositorysearch.data.SearchResponseDTO
import ru.dvn.githubrepositorysearch.domain.Repository
import ru.dvn.githubrepositorysearch.utils.toSearchResponse

class SearchPresenter(
    private val repository: Repository
) {
    private var view: SearchView? = null

    val callback = object : Callback<SearchResponseDTO> {
        override fun onResponse(call: Call<SearchResponseDTO>, response: Response<SearchResponseDTO>) {
            onSuccessRequest(response)
        }

        override fun onFailure(call: Call<SearchResponseDTO>, t: Throwable) {
            onError(t.message.toString())
        }
    }

    fun attach(view: SearchView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    fun search(query: String?) {
        if (query.isNullOrBlank()) {
            view?.showError("Запрос не может быть пустым")
            return
        }

        repository.search(query, callback)
    }

    fun onSuccessRequest(response: Response<SearchResponseDTO>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val data = body.toSearchResponse()
                view?.showData(data)
            } else {
                view?.showError("Пустой ответ")
            }
        } else {
            view?.showError("Ошибка сервера")
        }
    }

    fun onError(message: String) {
        view?.showError("Не удалось отправить запрос: $message")
    }
}