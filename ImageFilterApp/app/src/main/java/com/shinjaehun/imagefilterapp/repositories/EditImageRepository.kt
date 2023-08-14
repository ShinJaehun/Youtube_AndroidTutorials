package com.shinjaehun.imagefilterapp.repositories

import android.graphics.Bitmap
import android.net.Uri
import com.shinjaehun.imagefilterapp.data.ImageFilter

interface EditImageRepository {
    suspend fun prepareImagePreview(imageUri: Uri): Bitmap?

    suspend fun getImageFilters(image: Bitmap): List<ImageFilter>
}