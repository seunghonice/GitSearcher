package com.hongsyong.gitsearcher.data.repository

import com.hongsyong.gitsearcher.data.model.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/search/repositories")
    fun getRepositories(@Query("q") query: String, @Query("page") page: Int): Call<ResponseBody>

}