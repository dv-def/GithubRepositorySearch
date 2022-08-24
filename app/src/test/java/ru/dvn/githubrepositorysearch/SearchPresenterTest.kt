package ru.dvn.githubrepositorysearch

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import retrofit2.Response
import ru.dvn.githubrepositorysearch.data.SearchResponseDTO
import ru.dvn.githubrepositorysearch.data.SearchResultDTO
import ru.dvn.githubrepositorysearch.domain.Repository
import ru.dvn.githubrepositorysearch.presentation.SearchPresenter
import ru.dvn.githubrepositorysearch.presentation.SearchView
import ru.dvn.githubrepositorysearch.utils.toSearchResponse

class SearchPresenterTest {
    private lateinit var presenter: SearchPresenter

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var view: SearchView

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        presenter = SearchPresenter(repository)
        presenter.attach(view)
    }

    @Test
    fun searchTest() {
        val query = "my query"
        presenter.search(query)

        verify(repository, times(1)).search(query, presenter.callback)
    }

    @Test
    fun searchTest_showError() {
        val response = mock(Response::class.java) as Response<SearchResponseDTO>

        `when`(response.isSuccessful).thenReturn(false)

        presenter.onError("Ошибка сервера")
        verify(view, times(1)).showError("Не удалось отправить запрос: Ошибка сервера")
    }

    @Test
    fun searchTest_showData() {
        val response = mock(Response::class.java) as Response<SearchResponseDTO>

        `when`(response.isSuccessful).thenReturn(true)

        val searchResponse = SearchResponseDTO(
            totalCount = 1,
            items = listOf(
                SearchResultDTO(
                    name = "test",
                    fullName = "testing",
                    description = "desc",
                    language = "kotlin",
                    visibility = null
                )
            )
        )

        `when`(response.body()).thenReturn(searchResponse)

        presenter.onSuccessRequest(response)

        verify(view, times(1)).showData(searchResponse.toSearchResponse())
    }

    @Test
    fun searchTest_emptyResponse() {
        val response = mock(Response::class.java) as Response<SearchResponseDTO>
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(null)

        presenter.onSuccessRequest(response)
        verify(view, times(1)).showError("Пустой ответ")
    }

    @Test
    fun searchTest_emptyQuery() {
        val query = ""

        presenter.search(query)
        verify(view, times(1)).showError("Запрос не может быть пустым")
    }

    @Test
    fun searchTest_blankQuery() {
        val query = "     "
        presenter.search(query)
        verify(view, times(1)).showError("Запрос не может быть пустым")
    }

    @After
    fun clean() {
        presenter.detach()
    }
}