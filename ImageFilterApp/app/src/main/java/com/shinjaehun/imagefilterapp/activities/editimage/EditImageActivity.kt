package com.shinjaehun.imagefilterapp.activities.editimage

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shinjaehun.imagefilterapp.activities.main.MainActivity
import com.shinjaehun.imagefilterapp.databinding.ActivityEditImageBinding
import com.shinjaehun.imagefilterapp.utilities.displayToast
import com.shinjaehun.imagefilterapp.utilities.show
import com.shinjaehun.imagefilterapp.viewmodels.EditImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditImageBinding

    private val viewModel: EditImageViewModel by viewModel()

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
        viewModel.imagePreviewUiState.observe(this, {
            val dataState = it ?: return@observe
            binding.pbPreview.visibility =
                if (dataState.isLoading) View.VISIBLE else View.GONE
            dataState.bitmap?.let {  bitmap ->
                binding.ivPreview.setImageBitmap(bitmap)
                binding.ivPreview.show()
            } ?: kotlin.run {
                dataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun prepareImagePreview() {
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
    }
}