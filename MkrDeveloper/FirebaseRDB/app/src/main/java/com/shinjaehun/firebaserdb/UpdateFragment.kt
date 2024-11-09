package com.shinjaehun.firebaserdb

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil3.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shinjaehun.firebaserdb.databinding.FragmentUpdateBinding
import com.shinjaehun.firebaserdb.models.Contact

private const val TAG = "UpdateFragment"

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args : UpdateFragmentArgs by navArgs()
    private lateinit var firebaseRef : DatabaseReference
    private lateinit var storageRef : StorageReference

    private var uri: Uri? = null
    private var imageUri: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
        storageRef = FirebaseStorage.getInstance().getReference("img")

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.imgUpdate.setImageURI(it)
            if(it != null) {
                Log.i(TAG, "uri: $it")
                uri = it
            }
        }

        imageUri = args.imgUri

        binding.apply {
            edtUpdateName.setText(args.name)
            edtUpdatePhone.setText(args.phone)
            imgUpdate.load(imageUri)

            btnUpdate.setOnClickListener { 
                updateData()
                findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }

            imgUpdate.setOnClickListener {
                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setTitle("changing the image")
                        .setMessage("please select the option")
                        .setPositiveButton("change image") { _, _ ->
                            pickImage.launch("image/*")
                        }
                        .setNegativeButton("delete image") { _, _ ->
                            imageUri = null
                            binding.imgUpdate.setImageResource(R.drawable.logo)
                        }
                        .setNeutralButton("cancel") { _, _ ->

                        }
                        .show()

                }
            }
        }

        return binding.root
    }

    private fun updateData() {
        val name = binding.edtUpdateName.text.toString()
        val phone = binding.edtUpdatePhone.text.toString()
//        val contact = Contact(args.id, name, phone)

//        firebaseRef.child(args.id).setValue(contact)
//            .addOnCompleteListener {
//                Toast.makeText(context, "data updated successfully", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
//            }

        var contact : Contact

        if (uri != null) {
            storageRef.child(args.id).putFile(uri!!)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            imageUri = url.toString()
                            contact = Contact(args.id, name, phone, imageUri)

                            firebaseRef.child(args.id).setValue(contact)
                                .addOnCompleteListener {
                                    Toast.makeText(context, "data updated successfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
                                }

                        }
                }
        } else {
            contact = Contact(args.id, name, phone, imageUri)

            firebaseRef.child(args.id).setValue(contact)
                .addOnCompleteListener {
                    Toast.makeText(context, "data updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
                }

        }

        if (imageUri == null) {
            storageRef.child(args.id).delete()
        }
    }

}