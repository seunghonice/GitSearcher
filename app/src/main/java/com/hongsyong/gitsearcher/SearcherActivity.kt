package com.hongsyong.gitsearcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hongsyong.gitsearcher.databinding.ActivitySearcherBinding
import com.hongsyong.gitsearcher.viewmodel.SearcherViewModel
import com.hongsyong.gitsearcher.viewmodel.ViewModelFactory

class SearcherActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearcherBinding
    private lateinit var vm: SearcherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searcher)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_searcher)
        binding.vm = ViewModelProvider(
            this,
            ViewModelFactory.getInstance()
        ).get(SearcherViewModel::class.java)
        vm = binding.vm as SearcherViewModel

    }
}