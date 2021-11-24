package com.hongsyong.gitsearcher.data.repository

import com.hongsyong.gitsearcher.data.model.ResponseBody
import retrofit2.Call

/**
 * Searcher Repository
 *
 * DataSource 추상화
 */
object SearcherRepository {
    private val dataSource = SearcherDataSource

    /**
     *  github repository 검색
     */
    fun searchRepositories(query: String, page: Int): Call<ResponseBody> {
        return dataSource.searchRepositories(query, page)
    }
}