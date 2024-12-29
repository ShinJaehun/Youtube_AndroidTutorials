package com.shinjaehun.roomapp.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.shinjaehun.roomapp.R
import com.shinjaehun.roomapp.databinding.FragmentUpdateBinding
import com.shinjaehun.roomapp.model.Address
import com.shinjaehun.roomapp.model.User
import com.shinjaehun.roomapp.viewmodel.UserViewModel

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

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

        binding.updateBtn.setOnClickListener {
//            updateItem()
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

//    private fun updateItem() {
//        val firstName = binding.updateFirstNameEt.text.toString()
//        val lastName = binding.updateLastNameEt.text.toString()
//        val age = Integer.parseInt(binding.updateAgeEt.text.toString())
//        val streetName = binding.updateStreetNameEt.text.toString()
//        val streetNumber = Integer.parseInt(binding.updateStreetNumberEt.text.toString())
//
//        if (inputCheck(firstName, lastName, binding.updateAgeEt.text)) {
//            val updatedUser = User(args.currentUser.id, firstName, lastName, age, Address(streetName, streetNumber))
//            mUserViewModel.updateUser(updatedUser)
//            Toast.makeText(requireContext(), "updated successfully", Toast.LENGTH_SHORT).show()
//            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
//        } else {
//            Toast.makeText(requireContext(), "please fill out all fields", Toast.LENGTH_SHORT).show()
//        }
//
//    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }
}