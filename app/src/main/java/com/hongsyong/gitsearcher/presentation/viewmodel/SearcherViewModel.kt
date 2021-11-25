package com.hongsyong.gitsearcher.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hongsyong.gitsearcher.data.model.Repository
import com.hongsyong.gitsearcher.data.model.ResponseBody
import com.hongsyong.gitsearcher.data.repository.SearcherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.await

class SearcherViewModel : ViewModel() {
    private val TAG = "SEARCH"

    val processing = ObservableBoolean(false)
    val noResult = ObservableBoolean(false)

    val results = MutableLiveData<List<Repository>>()
    val page = ObservableInt(1)
    val maxCount = ObservableInt(1)

    private var searchingJob: Call<ResponseBody>? = null

    fun searchRepositories(query: String, page: Int) {
        // 진행중이었다면 취소
        if (processing.get()) cancelSearch()

        this.page.set(page)
        if (!processing.get()) processing.set(true)
        noResult.set(false)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "불러오기 시작~ !")
                searchingJob = SearcherRepository.searchRepositories(query, this@SearcherViewModel.page.get())
                val body = searchingJob?.await()

                val count = body?.totalCount ?: 0

                // 데이터가 없는 경우
                if (count == 0) {
                    results.postValue(null)
                    noResult.set(true)
                    processing.set(false)
                    Log.d(TAG, "불러오기 끝 (없음) !")
                    return@launch
                }

                val items = body?.items!!
                if (this@SearcherViewModel.page.get() > 1) {
                    if (items.isNotEmpty()) {
                        // append
                        results.postValue(results.value?.plus(items))
                    } else {
                        // 마지막 페이지의 다음페이지라는 뜻. 없음
                    }
                } else {
                    // set
                    results.postValue(items)
                    maxCount.set(count)
                }

                noResult.set(count == 0)
                processing.set(false)
                Log.d(TAG, "불러오기 끝 !")
            } catch (e: Exception) {
                e.printStackTrace()
                processing.set(false)
            }
        }
    }

    fun cancelSearch() {
        // processing 중일때만 수행하도록
        if (!processing.get()) return

        searchingJob?.cancel()
        searchingJob = null
        Log.d(TAG, "불러오기 취소 !")
        processing.set(false)
    }
}