package com.shinjaehun.mvvmtvshows.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.adapters.TVShowsAdapter
import com.shinjaehun.mvvmtvshows.databinding.ActivityMainBinding
import com.shinjaehun.mvvmtvshows.listeners.TVShowsListener
import com.shinjaehun.mvvmtvshows.models.TVShow
import com.shinjaehun.mvvmtvshows.repositories.MostPopularTVShowsRepository
import com.shinjaehun.mvvmtvshows.util.Resource
import com.shinjaehun.mvvmtvshows.viewmodels.MostPopularTVShowsViewModel
import com.shinjaehun.mvvmtvshows.viewmodels.TVShowsViewModelFactory


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), TVShowsListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MostPopularTVShowsViewModel
    private var tvShows: MutableList<TVShow> = mutableListOf()
    private lateinit var adapter: TVShowsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 원본은 이건데... 걍 상관 없음
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        doInitialization()
        getMostPopularTVShows()
    }

    private fun doInitialization() {
        binding.tvShowsRecyclerView.setHasFixedSize(true) // 이거 왜 하는지 모르겠음...
        val repository = MostPopularTVShowsRepository()
        val viewModelProviderFactory = TVShowsViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MostPopularTVShowsViewModel::class.java)

        // JAVA에서는 걍 이것만 해도 되던데...
        // 여기선 오류 발생: java.lang.RuntimeException: Unable to start activity ComponentInfo{com.shinjaehun.mvvmtvshows/com.shinjaehun.mvvmtvshows.activities.MainActivity}: java.lang.RuntimeException: Cannot create an instance of class com.shinjaehun.mvvmtvshows.viewmodels.MostPopularTVShowsViewModel
        // viewModel = ViewModelProvider(this).get(MostPopularTVShowsViewModel::class.java)

        // 이게 실패했던 이유는 무엇일까??
        adapter = TVShowsAdapter(tvShows, layoutInflater,this)
        binding.tvShowsRecyclerView.adapter = adapter

        // 일단 이걸 꼭 여기서 한번 실행해야 하는겨?
        viewModel.getMostPopularTVShows(0)
    }

    private fun getMostPopularTVShows() {
        // adapter를 따로 하면 항상 실패하고 이것만 성공했었음...
//        viewModel.getMostPopularTVShows(0)
//        viewModel.mostPopularTVShows.observe(this, Observer { response ->
//            when(response) {
//                is Resource.Success ->
//                    response.data?.let { tvShowsResponse ->
//                        Toast.makeText(applicationContext,
//                            "Total pages ${tvShowsResponse.pages}", Toast.LENGTH_SHORT).show()
//
//                        TVShowsAdapter(tvShowsResponse.tv_shows, layoutInflater,this).also { adapter ->
//                            with(binding.tvShowsRecyclerView) {
//                                this.adapter = adapter
//                            }
//                        }
//                    }
//                is Resource.Error ->
//                    response.message?.let { message ->
//                        Toast.makeText(applicationContext,
//                            "An error occured: $message", Toast.LENGTH_SHORT).show()
//                    }
//            }
//        })

        // 그래도 이렇게 해서 성공시킴!!! 포인트는 데이터 추가 후 adapter에 알림
        binding.isLoading = true
        viewModel.mostPopularTVShows.observe(this, Observer { response ->
            binding.isLoading = false
            when(response) {
                is Resource.Success ->
                    response.data?.let { tvShowsResponse ->
                        Toast.makeText(applicationContext,
                            "Total pages ${tvShowsResponse.pages}", Toast.LENGTH_SHORT).show()

                        // 변경하고나서 변경된 사항을 알려야 반영되지...
                        tvShows.addAll(tvShowsResponse.tv_shows)
                        adapter.notifyDataSetChanged()

                    }
                is Resource.Error ->
                    response.message?.let { message ->
                        Toast.makeText(applicationContext,
                            "An error occured: $message", Toast.LENGTH_SHORT).show()
                    }
            }
        })
    }

    override fun onTVShowClick(tvShow: TVShow) {
        Log.i(TAG, "clicked!")
    }
}