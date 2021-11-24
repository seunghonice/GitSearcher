package com.hongsyong.gitsearcher.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hongsyong.gitsearcher.R
import com.hongsyong.gitsearcher.databinding.ActivitySearcherBinding
import com.hongsyong.gitsearcher.presentation.adapter.ResultAdapter
import com.hongsyong.gitsearcher.presentation.viewmodel.SearcherViewModel
import com.hongsyong.gitsearcher.presentation.viewmodel.ViewModelFactory

class SearcherActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearcherBinding
    private lateinit var vm: SearcherViewModel

    private lateinit var resultAdapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_searcher)
        binding.viewmodel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance()
        ).get(SearcherViewModel::class.java)
        vm = binding.viewmodel as SearcherViewModel

        viewSetting()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun viewSetting() {
        binding.run {

            // 검색바 터치 동작
            etSearchBar.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    cancelSearching()
                    v.requestFocus()
                    showSoftKeyboard(v)
                    return@setOnTouchListener true
                }
                return@setOnTouchListener false
            }

            // 검색바 작성 중 엔터
            etSearchBar.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) { // 엔터
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
            // 검색바 검색버튼 클릭
            etSearchBar.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) // 검색버튼 클릭
                    return@setOnEditorActionListener search()
                return@setOnEditorActionListener false
            }
            // 텍스트 입력 시 동작
            etSearchBar.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (vm.processing.get()) {
                        cancelSearching()
                    }
                    // 검색어 삭제버튼 표시
                    ivCancel.visibility = if (s?.isEmpty() == true) View.GONE else View.VISIBLE
                }
            })

            // 검색 이미지 버튼 클릭
            ivSearch.setOnClickListener {
                search()
            }

            // 검색어 삭제 버튼 클릭
            ivCancel.setOnClickListener {
                etSearchBar.text.clear()
                vm.noResult.set(false)
                etSearchBar.requestFocus()
            }

            // 결과 리스트
            resultAdapter = ResultAdapter(vm.results.value, this@SearcherActivity)
            rvResults.adapter = resultAdapter
            rvResults.layoutManager = LinearLayoutManager(application)
            rvResults.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val totalItemCount = recyclerView.adapter!!.itemCount - 1
                    if (!recyclerView.canScrollVertically(1) && !vm.processing.get()) {
                        // 마지막 아이템이 아닐 때에만 수행 -> 다 불러왔으면 수행하지 않도록
                        if (totalItemCount + 1 < vm.maxCount.get()) {
                            search(vm.page.get() + 1)
                        }
                    }
                }
            })

            // 결괏값이 업데이트되면 adapter 갱신
            vm.results.observe(this@SearcherActivity, {
                resultAdapter.results = it
                resultAdapter.notifyDataSetChanged()
            })
        }
    }

    private fun search(page: Int = 1): Boolean {
        binding.run {
            if (etSearchBar.text.isEmpty()) {
                Toast.makeText(this@SearcherActivity, "검색어를 입력해주세요 !", Toast.LENGTH_SHORT).show()
                etSearchBar.requestFocus()
                return false
            }

            // search 중이었다면 cancel
            if (vm.processing.get())
                cancelSearching()

            // search 시작!
            vm.page.set(page)
            vm.searchRepositories(etSearchBar.text.toString())
            etSearchBar.clearFocus()
        }
        return true
    }

    private fun cancelSearching() {
        // processing 중일때만 수행하도록
        if (!vm.processing.get()) return

        vm.cancelSearch()
    }
}