package com.hongsyong.gitsearcher.presentation.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hongsyong.gitsearcher.data.model.Repository

class SearcherViewModel : ViewModel() {
    val processing = ObservableBoolean(false)
    val noResult = ObservableBoolean(false)

    val results = MutableLiveData<List<Repository>>()
    val page = ObservableInt(1)
    val maxCount = ObservableInt(1)

    fun searchRepositories(query: String) {

    }

//    private var searchingJob: Call<ResponseBody>? = null

//    fun searchRepositories(query: String) {
//        if (!processing.get()) processing.set(true)
//        noResult.set(false)
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                Log.d("shhong", "불러오기 시작~ !")
//                searchingJob = SearcherRepository.searchRepositories(query, page.get())
//                val body = searchingJob?.await()
//
//                val count = body?.totalCount ?: 0
//
//                // 데이터가 없는 경우
//                if (count == 0) {
//                    results.postValue(null)
//                    noResult.set(true)
//                    processing.set(false)
//                    Log.d("shhong", "불러오기 끝 (없음) !")
//                    return@launch
//                }
//
//                val items = body?.items!!
//                if (page.get() > 1) {
//                    if (items.isNotEmpty()) {
//                        // append
//                        results.postValue(results.value?.plus(items))
//                    } else {
//                        // 마지막 페이지의 다음페이지라는 뜻. 없음
//                    }
//                } else {
//                    // set
//                    results.postValue(items)
//                    maxCount.set(count)
//                }
//
//                noResult.set(count == 0)
//                Log.d("shhong", "불러오기 끝 !")
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun cancelSearch() {
//        searchingJob?.cancel()
//        searchingJob = null
//        Log.d("shhong", "불러오기 취소 !")
//        processing.set(false)
//    }
}