package com.shinjaehun.roomapp.fragments.update

import android.graphics.BitmapFactory
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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil3.Bitmap
import coil3.load
import com.shinjaehun.roomapp.R
import com.shinjaehun.roomapp.databinding.FragmentUpdateBinding
import com.shinjaehun.roomapp.model.Address
import com.shinjaehun.roomapp.model.User
import com.shinjaehun.roomapp.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            binding.updateProfilePhotoIv.setImageURI(uri)
            binding.updateProfilePhotoIv.tag = uri.toString()
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.updateFirstNameEt.setText(args.currentUser.firstName)
        binding.updateLastNameEt.setText(args.currentUser.lastName)
        binding.updateAgeEt.setText(args.currentUser.age.toString())
        binding.updateStreetNameEt.setText(args.currentUser.address.streetName)
        binding.updateStreetNumberEt.setText(args.currentUser.address.streetNumber.toString())
        binding.updateProfilePhotoIv.load(args.currentUser.profilePhoto)

        binding.updateProfilePhotoIv.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.updateBtn.setOnClickListener {
            updateItem()
        }

        binding.deleteIv.setOnClickListener {
            deleteItem()
        }

        return binding.root
    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(), "${args.currentUser.firstName} deleted successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }

    private fun updateItem() {
        val firstName = binding.updateFirstNameEt.text.toString()
        val lastName = binding.updateLastNameEt.text.toString()
        val age = binding.updateAgeEt.text
        val streetName = binding.updateStreetNameEt.text.toString()
        val streetNumber = binding.updateStreetNumberEt.text

        if (binding.updateProfilePhotoIv.tag == null) {
            if (inputCheck(firstName, lastName, age)) {
                val user = User(
                    args.currentUser.id,
                    firstName,
                    lastName,
                    Integer.parseInt(age.toString()),
                    Address(streetName, Integer.parseInt(streetNumber.toString())),
                    profilePhoto = args.currentUser.profilePhoto
                )
                mUserViewModel.updateUser(user)
                Toast.makeText(requireContext(), "successfully updated!", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), "fill out all fields!", Toast.LENGTH_LONG)
                    .show()
            }
        } else {

            val bmOptions = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
                requireContext().contentResolver.openInputStream(Uri.parse(binding.updateProfilePhotoIv.tag.toString()))?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream, null, this)
                }
                val targetW: Int = 150
                val targetH: Int = 150
                val scaleFactor: Int = Math.min(outWidth / targetW, outHeight / targetH)
                inJustDecodeBounds = false
                inSampleSize = scaleFactor
            }

            requireContext().contentResolver.openInputStream(Uri.parse(binding.updateProfilePhotoIv.tag.toString()))?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->

                    lifecycleScope.launch {
                        if (inputCheck(firstName, lastName, age)) {
                            val user = User(
                                args.currentUser.id,
                                firstName,
                                lastName,
                                Integer.parseInt(age.toString()),
                                Address(streetName, Integer.parseInt(streetNumber.toString())),
                                profilePhoto = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height)
                            )
                            mUserViewModel.updateUser(user)
                            Toast.makeText(requireContext(), "successfully updated!", Toast.LENGTH_LONG)
                                .show()
                            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                        } else {
                            Toast.makeText(requireContext(), "fill out all fields!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }
}