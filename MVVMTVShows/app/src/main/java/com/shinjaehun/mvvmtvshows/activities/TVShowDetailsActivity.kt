package com.shinjaehun.mvvmtvshows.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.adapters.ImageSliderAdapter
import com.shinjaehun.mvvmtvshows.databinding.ActivityTvshowDetailsBinding
import com.shinjaehun.mvvmtvshows.repositories.TVShowDetailsRepository
import com.shinjaehun.mvvmtvshows.viewmodels.TVShowDetailsViewModel
import com.shinjaehun.mvvmtvshows.viewmodels.TVShowDetailsViewModelFactory

class TVShowDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTvshowDetailsBinding
    private lateinit var viewModel: TVShowDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvshowDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        doInitialization()
        getTVShowDetails()
    }

    private fun doInitialization() {
        val repository = TVShowDetailsRepository()
        val viewModelProviderFactory = TVShowDetailsViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(TVShowDetailsViewModel::class.java)
    }

    private fun getTVShowDetails() {
        binding.isLoading = true

        val tvShowId = intent.getIntExtra("id", -1).toString()
        viewModel.getTVShowDetails(tvShowId)

        viewModel.tvShowDetails.observe(this, Observer { response ->
            binding.isLoading = false
//            Toast.makeText(applicationContext, response.tvShow.url, Toast.LENGTH_SHORT).show()

            if (response != null) {
                loadImageSlider(response.tvShow.pictures)
            }

        })
    }

    private fun loadImageSlider(sliderImages : List<String>) {
        binding.sliderViewPager.offscreenPageLimit = 1
        binding.sliderViewPager.adapter = ImageSliderAdapter(sliderImages, layoutInflater)
        binding.sliderViewPager.visibility = View.VISIBLE
        binding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicators(sliderImages.size)
        binding.sliderViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount = binding.layoutSliderIndicators.childCount
        for (i: Int in 0 until childCount) {
            val imageView: ImageView = binding.layoutSliderIndicators.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.background_slider_indicator_active))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.background_slider_indicator_inactive))
            }
        }
    }

    private fun setupSliderIndicators(count: Int) {
        val indicators : Array<ImageView?> = arrayOfNulls<ImageView>(count)
        // 이 자바 코드를 코틀린으로 옮기는 게 정말 어려웠음...ㅠㅠ
        // ImageView[] indicators = new ImageView[count];

        // https://stackoverflow.com/questions/35253368/how-can-i-create-an-array-in-kotlin-like-in-java-by-just-providing-a-size
        Log.i(TAG, "number of indicators: ${indicators.size}")
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL
        // 굳이 이렇게 할 필요 없음...
        // layout에 constraint가 걸려 있었음.....

        for (i: Int in 0 until count) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(ContextCompat.getDrawable(
                applicationContext,
                R.drawable.background_slider_indicator_inactive
            ))
            indicators[i]?.layoutParams = layoutParams
            binding.layoutSliderIndicators.addView(indicators[i])
        }
        binding.layoutSliderIndicators.visibility = View.VISIBLE
    }
}
