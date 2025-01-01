package com.shinjaehun.roomapp.fragments.add

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.toBitmap
import com.shinjaehun.roomapp.R
import com.shinjaehun.roomapp.model.User
import com.shinjaehun.roomapp.viewmodel.UserViewModel
import com.shinjaehun.roomapp.databinding.FragmentAddBinding
import com.shinjaehun.roomapp.model.Address
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            binding.profilePhotoIv.setImageURI(uri)
            binding.profilePhotoIv.tag = uri.toString()
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.profilePhotoIv.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.addBtn.setOnClickListener{
            insertDataToDatabase()
        }

        return binding.root
    }


    @SuppressLint("NewApi")
    private fun insertDataToDatabase() {
        val firstName = binding.addFirstNameEt.text.toString()
        val lastName = binding.addLastNameEt.text.toString()
        val age = binding.addAgeEt.text
        val streetName = binding.streetNameEt.text.toString()
        val streetNumber = binding.streetNumberEt.text

        val bmOptions = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            requireContext().contentResolver.openInputStream(Uri.parse(binding.profilePhotoIv.tag.toString()))?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, this)
            }
            val targetW: Int = 100
            val targetH: Int = 100
            val scaleFactor: Int = Math.min(outWidth / targetW, outHeight / targetH)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        requireContext().contentResolver.openInputStream(Uri.parse(binding.profilePhotoIv.tag.toString()))?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->


                lifecycleScope.launch {
                    if (inputCheck(firstName, lastName, age)) {
                        val user = User(
                            0,
                            firstName,
                            lastName,
                            Integer.parseInt(age.toString()),
                            Address(streetName, Integer.parseInt(streetNumber.toString())),
                            profilePhoto = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height)
                        )
                        mUserViewModel.addUser(user)
                        Toast.makeText(requireContext(), "successfully added!", Toast.LENGTH_LONG)
                            .show()
                        findNavController().navigate(R.id.action_addFragment_to_listFragment)
                    } else {
                        Toast.makeText(requireContext(), "fill out all fields!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }


    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

//    private suspend fun getBitmap(): Bitmap {
//        lateinit var bitmap: Bitmap
//        val loading = ImageLoader(requireContext())
//        val request = ImageRequest.Builder(requireContext())
//            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
//            .target {
//                bitmap = it.toBitmap()
//            }
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult)
////        return (result as BitmapDrawable).bitmap
//        return bitmap
//    }
//
//
//    private fun calculateInSampleSize(
//        options: BitmapFactory.Options,
//        reqWidth: Int,
//        reqHeight: Int
//    ): Int {
//        // Raw height and width of image
//        val (height: Int, width: Int) = options.run { outHeight to outWidth }
//        var inSampleSize = 1
//
//        if (height > reqHeight || width > reqWidth) {
//
//            val halfHeight: Int = height / 2
//            val halfWidth: Int = width / 2
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
//                inSampleSize *= 2
//            }
//        }
//
//        return inSampleSize
//    }
//
//    private fun decodeSampledBitmapFromResource(
//        ist: InputStream,
//        url: String,
//        reqWidth: Int,
//        reqHeight: Int
//    ): Bitmap? {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        return BitmapFactory.Options().run {
//
//            inJustDecodeBounds = true
//            BitmapFactory.decodeStream(ist, null,this)
//
//            // Calculate inSampleSize
//            inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
//
//            // Decode bitmap with inSampleSize set
//            inJustDecodeBounds = false
//
//            BitmapFactory.decodeStream(URL(url).openStream(), null,this)
//        }
//    }
}