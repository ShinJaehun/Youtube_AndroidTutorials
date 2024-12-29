package com.shinjaehun.roomapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shinjaehun.roomapp.R
import com.shinjaehun.roomapp.model.User
import com.shinjaehun.roomapp.viewmodel.UserViewModel
import com.shinjaehun.roomapp.databinding.FragmentAddBinding
import com.shinjaehun.roomapp.model.Address

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

//        binding.addBtn.setOnClickListener{
//            insertDataToDatabase()
//        }

        return binding.root
    }

//    private fun insertDataToDatabase() {
//        val firstName = binding.addFirstNameEt.text.toString()
//        val lastName = binding.addLastNameEt.text.toString()
//        val age = binding.addAgeEt.text
//        val streetName = binding.streetNameEt.text.toString()
//        val streetNumber = binding.streetNumberEt.text
//
//        if (inputCheck(firstName, lastName, age)) {
//            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()), Address(streetName, Integer.parseInt(streetNumber.toString())))
//            mUserViewModel.addUser(user)
//            Toast.makeText(requireContext(), "successfully added!", Toast.LENGTH_LONG).show()
//            findNavController().navigate(R.id.action_addFragment_to_listFragment)
//        } else {
//            Toast.makeText(requireContext(), "fill out all fields!", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
//        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
//    }

}