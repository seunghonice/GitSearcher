package com.hongsyong.gitsearcher.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class SearcherViewModel : ViewModel() {
    val processing = ObservableBoolean(false)
    val noResult = ObservableBoolean(false)
}