package ru.dvn.githubrepositorysearch.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dvn.githubrepositorysearch.data.RepositoryImpl
import ru.dvn.githubrepositorysearch.data.SearchApi
import ru.dvn.githubrepositorysearch.domain.Repository

object Di {
    private const val BASE_URL = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildClient())
        .build()

    private val searchApi: SearchApi = retrofit.create(SearchApi::class.java)

    val repository: Repository = RepositoryImpl(searchApi)

    private fun buildClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }
}