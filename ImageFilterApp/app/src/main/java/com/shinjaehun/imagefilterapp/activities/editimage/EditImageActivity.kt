package com.shinjaehun.imagefilterapp.activities.editimage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.shinjaehun.imagefilterapp.activities.main.MainActivity
import com.shinjaehun.imagefilterapp.adapters.ImageFiltersAdapter
import com.shinjaehun.imagefilterapp.data.ImageFilter
import com.shinjaehun.imagefilterapp.databinding.ActivityEditImageBinding
import com.shinjaehun.imagefilterapp.listeners.ImageFilterListener
import com.shinjaehun.imagefilterapp.utilities.displayToast
import com.shinjaehun.imagefilterapp.utilities.show
import com.shinjaehun.imagefilterapp.viewmodels.EditImageViewModel
import jp.co.cyberagent.android.gpuimage.GPUImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity(), ImageFilterListener {
    private lateinit var binding: ActivityEditImageBinding
    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var gpuImage: GPUImage

    // Image bitmaps
    private lateinit var originalBitmap: Bitmap
    private val filteredBitmap = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
//        displayImagePreview()
        setupObservers()
        prepareImagePreview()
    }

    private fun setupObservers() {
//        // 얘 이것 좀 예쁘게 치워봐라...
//        viewModel.imagePreviewUiState.observe(this, {
//            val dataState = it ?: return@observe
//            binding.pbPreview.visibility =
//                if (dataState.isLoading) View.VISIBLE else View.GONE
//            dataState.bitmap?.let {  bitmap ->
//                binding.ivPreview.setImageBitmap(bitmap)
//                binding.ivPreview.show()
//                viewModel.loadImageFilters(bitmap)
//            } ?: kotlin.run {
//                dataState.error?.let { error ->
//                    displayToast(error)
//                }
//            }
//        })

        // 얘 이것 좀 예쁘게 치워봐라...
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.pbPreview.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let {  bitmap ->
                // For the first time 'filtered image = original image'
                originalBitmap = bitmap
                filteredBitmap.value = bitmap

                with(originalBitmap) {
                    gpuImage.setImage(this)
                    binding.ivPreview.show()
                    viewModel.loadImageFilters(this)
                }
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })

        viewModel.imageFiltersUiState.observe(this, {
            val imageFiltersDataState = it ?: return@observe
            binding.pbPreview.visibility =
                if(imageFiltersDataState.isLoading) View.VISIBLE else View.GONE
            imageFiltersDataState.imageFilters?.let {  imageFilters ->
                ImageFiltersAdapter(imageFilters, this).also {  adapter ->
                    binding.rvFilters.adapter = adapter
                }
            } ?: kotlin.run {
                imageFiltersDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })

        filteredBitmap.observe(this, { bitmap ->
            binding.ivPreview.setImageBitmap(bitmap)
        })
    }

    private fun prepareImagePreview() {
        gpuImage = GPUImage(applicationContext)

        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
            viewModel.prepareImagePreview(imageUri)
        }
    }

//    private fun displayImagePreview() {
//        intent.getParcelableExtra<Uri>(MainActivity.KEY_IMAGE_URI)?.let { imageUri ->
//            val inputStream = contentResolver.openInputStream(imageUri)
//            val bitmap = BitmapFactory.decodeStream(inputStream)
//            binding.ivPreview.setImageBitmap(bitmap)
//            binding.ivPreview.visibility = View.VISIBLE
//        }
//    }

    private fun setListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // this will show original image when long click the imageView until we release click,
        // so that we can see difference between original image and filtered image
        binding.ivPreview.setOnLongClickListener {
            binding.ivPreview.setImageBitmap(originalBitmap)
            return@setOnLongClickListener false
        }

        // 이게 있어서 그런지 몰라도 longclick 다음에 손을 놓으면 다시 filtered image가 표시된다.
        binding.ivPreview.setOnClickListener {
            binding.ivPreview.setImageBitmap(filteredBitmap.value)
        }
    }

    override fun onFilterSelected(imageFilter: ImageFilter) {
        with(imageFilter) {
            with(gpuImage) {
                setFilter(filter)
                filteredBitmap.value = bitmapWithFilterApplied
                // 근데 얘 bitmapWithFilterApplied는 뭐냐?
            }
        }
    }
}