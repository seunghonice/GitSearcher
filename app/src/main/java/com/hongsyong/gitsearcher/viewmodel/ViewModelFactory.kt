package com.hongsyong.gitsearcher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory private constructor() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return with(modelClass) {
            when {
                isAssignableFrom(SearcherViewModel::class.java) ->
                    SearcherViewModel()
                else -> null
            }
        } as T
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(): ViewModelFactory {
            if (INSTANCE == null)
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory()
                }
            return INSTANCE!!
        }
    }
}