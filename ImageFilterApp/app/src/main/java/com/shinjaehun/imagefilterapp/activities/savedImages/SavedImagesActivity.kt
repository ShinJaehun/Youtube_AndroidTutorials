package com.shinjaehun.imagefilterapp.activities.savedImages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import com.shinjaehun.imagefilterapp.activities.editimage.EditImageActivity
import com.shinjaehun.imagefilterapp.activities.filteredimage.FilteredImageActivity
import com.shinjaehun.imagefilterapp.adapters.SavedImagesAdapter
import com.shinjaehun.imagefilterapp.databinding.ActivitySavedImagesBinding
import com.shinjaehun.imagefilterapp.listeners.SavedImageListener
import com.shinjaehun.imagefilterapp.utilities.displayToast
import com.shinjaehun.imagefilterapp.viewmodels.SavedImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class SavedImagesActivity : AppCompatActivity(), SavedImageListener {

    private lateinit var binding: ActivitySavedImagesBinding
    private val viewModel: SavedImagesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        setListener()
        viewModel.loadSavedImages()
    }

    private fun setupObserver() {
        viewModel.savedImagesUiState.observe(this, {
            val savedImagesDataState = it ?: return@observe
            binding.pbSavedImages.visibility =
                if (savedImagesDataState.isLoading) View.VISIBLE else View.GONE
            savedImagesDataState.savedImages?.let { savedImages ->
//                displayToast("${savedImages.size} images loaded")
                SavedImagesAdapter(savedImages, this).also { adapter ->
                    with(binding.rvSavedImages) {
                        this.adapter = adapter
                        visibility = View.VISIBLE
                    }
                }
            } ?: run {
                savedImagesDataState.error?.let { error ->
                    displayToast(error)
                }
            }
        })
    }

    private fun setListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onImageClicked(file: File) {
        val fileUri = FileProvider.getUriForFile(
            applicationContext,
            "${packageName}.provider",
            file
        )
        Intent(applicationContext, FilteredImageActivity::class.java).also { filteredImageIntent ->
            filteredImageIntent.putExtra(EditImageActivity.KEY_FILTERED_IMAGE_URI, fileUri)
            startActivity(filteredImageIntent)
        }
    }
}