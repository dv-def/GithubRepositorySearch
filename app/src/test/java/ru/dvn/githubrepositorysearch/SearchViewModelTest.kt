package ru.dvn.githubrepositorysearch

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.robolectric.annotation.Config
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import ru.dvn.githubrepositorysearch.data.RepositoryImpl
import ru.dvn.githubrepositorysearch.domain.SearchResponse
import ru.dvn.githubrepositorysearch.presentation.SearchViewModel

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    @Mock
    private lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        searchViewModel = SearchViewModel(repository)
    }

    @Test
    fun test_RequestIsSuccessful() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<SearchViewModel.SearchState>{}
            val liveData = searchViewModel.getLiveData()

            val query = "some query"

            `when`(repository.search(query)).thenReturn(
                SearchResponse(1, listOf())
            )

            try {
                liveData.removeObserver(observer)
                searchViewModel.search(query)
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun test_RequestIsError() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<SearchViewModel.SearchState>{}
            val liveData = searchViewModel.getLiveData()

            val query = "some query"

            `when`(repository.search(query)).thenReturn(
                SearchResponse(0, null)
            )

            try {
                liveData.removeObserver(observer)
                searchViewModel.search(query)

                val value: SearchViewModel.SearchState.Error = liveData.value as SearchViewModel.SearchState.Error
                Assert.assertEquals(value.throwable.message, "Items are null")
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }
}