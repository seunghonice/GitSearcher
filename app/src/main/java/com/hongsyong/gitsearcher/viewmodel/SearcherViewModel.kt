package com.hongsyong.gitsearcher.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hongsyong.gitsearcher.model.Repository

class SearcherViewModel : ViewModel() {
    val processing = ObservableBoolean(false)
    val noResult = ObservableBoolean(false)

    val results = MutableLiveData<List<Repository>>()
    val page = ObservableInt(1)
    val maxCount = ObservableInt(1)

    fun searchRepositories(query: String) {

    }
}