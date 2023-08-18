package com.shinjaehun.mvvmtvshows.activities

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.adapters.ImageSliderAdapter
import com.shinjaehun.mvvmtvshows.databinding.ActivityTvShowDetailsBinding
import com.shinjaehun.mvvmtvshows.repositories.TVShowDetailsRepository
import com.shinjaehun.mvvmtvshows.viewmodels.TVShowDetailsViewModel
import com.shinjaehun.mvvmtvshows.viewmodels.TVShowDetailsViewModelFactory
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.util.*

private const val TAG = "TVShowDetailsActivity"

class TVShowDetailsActivity : AppCompatActivity() {

    private lateinit var activityTvShowDetailsBinding: ActivityTvShowDetailsBinding
    private lateinit var tvShowDetailsViewModel: TVShowDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTvShowDetailsBinding = ActivityTvShowDetailsBinding.inflate(layoutInflater)
        setContentView(activityTvShowDetailsBinding.root)

        doInitialization()
        getTVShowDetails()
    }

    private fun doInitialization() {
        val repository = TVShowDetailsRepository()
        val viewModelProviderFactory = TVShowDetailsViewModelFactory(application, repository)
        tvShowDetailsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(TVShowDetailsViewModel::class.java)

        activityTvShowDetailsBinding.imageBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getTVShowDetails() {
        activityTvShowDetailsBinding.isLoading = true

        val tvShowId = intent.getIntExtra("id", -1).toString()

        tvShowDetailsViewModel.getTVShowDetails(tvShowId)
        tvShowDetailsViewModel.tvShowDetails.observe(this, Observer { response ->
            activityTvShowDetailsBinding.isLoading = false

            if (response != null) {
                Log.i(TAG, "response: ${response}")

                loadImageSlider(response.tvShow.pictures)

                // 이거 자체가 오류 발생!! BindingAdapters의 setImageURL()이 null
//                activityTvShowDetailsBinding.imageTvShow.visibility = View.VISIBLE
//                activityTvShowDetailsBinding.tvShowImageURL = response.tvShow.image_path

                // 그래서 imaveTVShow는 걍 여기서 직접 보여주기로
                activityTvShowDetailsBinding.imageTvShow.alpha = 0f
                Picasso.get()
                    .load(response.tvShow.image_path)
                    .noFade()
                    .into(activityTvShowDetailsBinding.imageTvShow, object : Callback{
                        override fun onSuccess() {
                            activityTvShowDetailsBinding.imageTvShow.animate().setDuration(300).alpha(1f).start()
                        }

                        override fun onError(e: Exception?) {

                        }
                    })

                activityTvShowDetailsBinding.description =
                    HtmlCompat.fromHtml(response.tvShow.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
                activityTvShowDetailsBinding.textDescription.visibility = View.VISIBLE
                activityTvShowDetailsBinding.textReadMore.visibility = View.VISIBLE
                activityTvShowDetailsBinding.textReadMore.setOnClickListener {
                    if (activityTvShowDetailsBinding.textReadMore.text.toString().equals("Read More")) {
                        activityTvShowDetailsBinding.textDescription.maxLines = Integer.MAX_VALUE
                        activityTvShowDetailsBinding.textDescription.ellipsize = null
                        activityTvShowDetailsBinding.textReadMore.text = "Read Less"
                    } else {
                        activityTvShowDetailsBinding.textDescription.maxLines = 4
                        activityTvShowDetailsBinding.textDescription.ellipsize = TextUtils.TruncateAt.END
                        activityTvShowDetailsBinding.textReadMore.text = "Read More"

                    }
                }

                activityTvShowDetailsBinding.rating =
                    String.format(
                        Locale.getDefault(),
                        "%.2f",
                        response.tvShow.rating.toDouble()
                    )
                if (response.tvShow.genres != null) {
                    activityTvShowDetailsBinding.genre =
                        response.tvShow.genres[0] // 하나만 보여주는 거야???
                } else {
                    activityTvShowDetailsBinding.genre = "N/A"
                }

                activityTvShowDetailsBinding.runtime = response.tvShow.runtime + "Min"
                activityTvShowDetailsBinding.viewDivider1.visibility = View.VISIBLE
                activityTvShowDetailsBinding.layoutMisc.visibility = View.VISIBLE
                activityTvShowDetailsBinding.icStar.visibility = View.VISIBLE
                activityTvShowDetailsBinding.icDot1.visibility = View.VISIBLE
                activityTvShowDetailsBinding.icDot2.visibility = View.VISIBLE
                activityTvShowDetailsBinding.viewDivider2.visibility = View.VISIBLE

                activityTvShowDetailsBinding.buttonWebsite.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(response.tvShow.url)
                    startActivity(intent)
                }
                activityTvShowDetailsBinding.buttonWebsite.visibility = View.VISIBLE


                activityTvShowDetailsBinding.buttonEpisodes.visibility = View.VISIBLE

                loadBasicTVShowDetails()

            }
        })
    }

    private fun loadBasicTVShowDetails() {
        activityTvShowDetailsBinding.tvShowName = intent.getStringExtra("name").toString()
        activityTvShowDetailsBinding.networkCountry =
            intent.getStringExtra("network").toString() + " (" +
                    intent.getStringExtra("country").toString() + ")"
        activityTvShowDetailsBinding.status = intent.getStringExtra("status").toString()
        activityTvShowDetailsBinding.startedDate = intent.getStringExtra("startDate").toString()

        activityTvShowDetailsBinding.textName.visibility = View.VISIBLE
        activityTvShowDetailsBinding.textNetworkCountry.visibility = View.VISIBLE
        activityTvShowDetailsBinding.textStatus.visibility = View.VISIBLE
        activityTvShowDetailsBinding.textStartedDate.visibility = View.VISIBLE

    }

    private fun loadImageSlider(sliderImages : List<String>) {
        activityTvShowDetailsBinding.sliderViewPager.offscreenPageLimit = 1
        activityTvShowDetailsBinding.sliderViewPager.adapter = ImageSliderAdapter(sliderImages, layoutInflater)
        activityTvShowDetailsBinding.sliderViewPager.visibility = View.VISIBLE
        activityTvShowDetailsBinding.viewFadingEdge.visibility = View.VISIBLE
        setupSliderIndicators(sliderImages.size)
        activityTvShowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentSliderIndicator(position)
            }
        })
    }

    private fun setCurrentSliderIndicator(position: Int) {
        val childCount = activityTvShowDetailsBinding.layoutSliderIndicators.childCount
        for (i: Int in 0 until childCount) {
            val imageView: ImageView = activityTvShowDetailsBinding.layoutSliderIndicators.getChildAt(i) as ImageView
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
            activityTvShowDetailsBinding.layoutSliderIndicators.addView(indicators[i])
        }
        activityTvShowDetailsBinding.layoutSliderIndicators.visibility = View.VISIBLE
    }
}
