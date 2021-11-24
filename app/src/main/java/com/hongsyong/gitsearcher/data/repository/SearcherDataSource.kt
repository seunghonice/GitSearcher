package com.hongsyong.gitsearcher.data.repository

import com.hongsyong.gitsearcher.data.model.ResponseBody
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Searcher DataSource
 *
 * Remote DataSource 로부터 데이터를 가져오는 역할
 */
object SearcherDataSource {
    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val retrofitService = retrofit.create(RetrofitService::class.java)

    /**
     *  repository 검색
     */
    fun searchRepositories(query: String, page: Int): Call<ResponseBody> {
        return retrofitService.getRepositories(query, page)
    }
}