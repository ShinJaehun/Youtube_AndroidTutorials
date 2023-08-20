package com.shinjaehun.mvvmtvshows.activities

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shinjaehun.mvvmtvshows.R
import com.shinjaehun.mvvmtvshows.adapters.EpisodesAdapter
import com.shinjaehun.mvvmtvshows.adapters.ImageSliderAdapter
import com.shinjaehun.mvvmtvshows.databinding.ActivityTvShowDetailsBinding
import com.shinjaehun.mvvmtvshows.databinding.LayoutEpisodesBottomSheetBinding
import com.shinjaehun.mvvmtvshows.models.TVShow
import com.shinjaehun.mvvmtvshows.repositories.TVShowDetailsRepository
import com.shinjaehun.mvvmtvshows.util.TempDataHolder
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

    private var episodesBottomSheetDialog: BottomSheetDialog? = null
    private lateinit var layoutEpisodesBottomSheetBinding: LayoutEpisodesBottomSheetBinding

    private lateinit var tvShow: TVShow
    private var isTVShowAvailableInWatchList: Boolean = false

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

        tvShow = intent.getSerializableExtra("tvShow") as TVShow

        checkTVShowInWatchList()
    }

    private fun checkTVShowInWatchList() {

        tvShowDetailsViewModel.getTVShowFromWatchList(tvShow.id.toString()).observe(this, Observer {
            if (it != null) {
                Log.i(TAG, "getTVShowFromWatchList result tvshow: ${it.name}")
                isTVShowAvailableInWatchList = true
                activityTvShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_added)
            }
        })

        // 븅시나 LiveData를 observe해야지!!
        // LiveData 💔 Null-safety
        // https://chao2zhang.medium.com/livedata-nullability-99ef4e01bc54
//        tvShowDetailsViewModel.getTVShowFromWatchList(tvShow.id.toString()).also {
//            if (it != null) {
//                Log.i(TAG, "getTVShowFromWatchList result tvshow: ${it}")
//                isTVShowAvailableInWatchList = true
//                activityTvShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_added)
//            }
//        }
    }

    private fun getTVShowDetails() {
        activityTvShowDetailsBinding.isLoading = true

//        val tvShowId = intent.getIntExtra("id", -1).toString()
        val tvShowId = tvShow.id.toString()

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
                activityTvShowDetailsBinding.buttonEpisodes.setOnClickListener {
                    if (episodesBottomSheetDialog == null) {
                        Log.i(TAG, "dialog is ready!")
                        episodesBottomSheetDialog = BottomSheetDialog(this@TVShowDetailsActivity)

                        layoutEpisodesBottomSheetBinding = LayoutEpisodesBottomSheetBinding.inflate(layoutInflater)

                        // 이렇게 난리 치지 않아도 작동함
//                        layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
//                            LayoutInflater.from(this@TVShowDetailsActivity),
//                            R.layout.layout_episodes_bottom_sheet,
//                            findViewById(R.id.episodeContainer),
//                            false
//                        )
                        episodesBottomSheetDialog!!.setContentView(layoutEpisodesBottomSheetBinding.root)

                        layoutEpisodesBottomSheetBinding.episodeRecyclerView.adapter =
                            EpisodesAdapter(response.tvShow.episodes, layoutInflater)

                        episodesBottomSheetDialog!!.show() // 아 씨발 이거 있다는 거 이제야 봤네

                        layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener {
                            episodesBottomSheetDialog!!.dismiss()
                        }
                    }
                }

                activityTvShowDetailsBinding.buttonWebsite.visibility = View.VISIBLE
                activityTvShowDetailsBinding.buttonEpisodes.visibility = View.VISIBLE

                loadBasicTVShowDetails()

                activityTvShowDetailsBinding.imageAddWatchlist.setOnClickListener {
                    if (isTVShowAvailableInWatchList) {
                        tvShowDetailsViewModel.removeTVShowFromWatchList(tvShow).also {
                            isTVShowAvailableInWatchList = false
                            TempDataHolder.IS_WATCHLIST_UPDATED = true
                            activityTvShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_watchlist)
                            Toast.makeText(applicationContext, "removed from watchlist", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        TempDataHolder.IS_WATCHLIST_UPDATED = true
                        tvShowDetailsViewModel.addWatchList(tvShow).also {
                            activityTvShowDetailsBinding.imageAddWatchlist.setImageResource(R.drawable.ic_added)
                            Toast.makeText(applicationContext, "Added to watchlist", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                activityTvShowDetailsBinding.imageAddWatchlist.visibility = View.VISIBLE
            }
        })
    }

    private fun loadBasicTVShowDetails() {
        activityTvShowDetailsBinding.tvShowName = tvShow.name
//        activityTvShowDetailsBinding.tvShowName = intent.getStringExtra("name").toString()
        activityTvShowDetailsBinding.networkCountry = tvShow.network  + " (" + tvShow.country + ")"
//        activityTvShowDetailsBinding.networkCountry =
//            intent.getStringExtra("network").toString() + " (" +
//                    intent.getStringExtra("country").toString() + ")"
        activityTvShowDetailsBinding.status = tvShow.status
//        activityTvShowDetailsBinding.status = intent.getStringExtra("status").toString()
        activityTvShowDetailsBinding.startedDate = tvShow.startDate
//        activityTvShowDetailsBinding.startedDate = intent.getStringExtra("startDate").toString()

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
