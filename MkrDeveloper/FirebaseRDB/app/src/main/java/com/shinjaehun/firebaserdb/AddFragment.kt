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
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shinjaehun.firebaserdb.databinding.FragmentAddBinding
import com.shinjaehun.firebaserdb.databinding.FragmentHomeBinding
import com.shinjaehun.firebaserdb.models.Contact

private const val TAG = "AddFragment"

class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseRef : DatabaseReference
    private lateinit var storageRef : StorageReference

    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")
        storageRef = FirebaseStorage.getInstance().getReference("img")

        binding.btnSend.setOnClickListener {
            saveData()
        }

        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
            binding.imgAdd.setImageURI(it)
            if(it != null) {
                Log.i(TAG, "uri: $it")
                uri = it
            }
        }

        binding.btnPickImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        return binding.root
    }

    private fun saveData() {
        val name = binding.edtName.text.toString()
        val phone = binding.edtPhone.text.toString()

        if (name.isEmpty()) binding.edtName.error = "write a name"
        if (phone.isEmpty()) binding.edtPhone.error = "write a phone number"

        val contactId = firebaseRef.push().key!!
//        val contact = Contact(contactId, name, phone)

        var contact : Contact
        uri?.let {
            storageRef.child(contactId).putFile(it)
                .addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl
                        .addOnSuccessListener { url ->
                            Toast.makeText(context, "Image stored successfully", Toast.LENGTH_SHORT).show()

                            val imgUrl = url.toString()
                            Log.i(TAG, "imgUrl: $imgUrl")
//                          uri: content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fbbbbbb.PNG
//                          imgUrl: https://firebasestorage.googleapis.com/v0/b/test-project-c364e.appspot.com/o/img%2F-OBEjpsBLjvvV0U8qz04?alt=media&token=62936bf8-0081-4113-9953-7516f121a7c8
                            contact = Contact(contactId, name, phone, imgUrl)

                            firebaseRef.child(contactId).setValue(contact)
                                .addOnCompleteListener {
                                    Toast.makeText(context, "data stored successfully", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                }
        }


    }
}