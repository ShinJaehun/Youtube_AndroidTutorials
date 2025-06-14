package com.shinjaehun.quizzyapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.databinding.FragmentSignUpBinding
import com.shinjaehun.quizzyapp.util.UiState
import com.shinjaehun.quizzyapp.util.isValidEmail
import com.shinjaehun.quizzyapp.util.toast
import com.shinjaehun.quizzyapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.signUpBtn.setOnClickListener {
            if (validation()) {
                authViewModel.signUp(
                    email = binding.editEmailSignUp.text.toString(),
                    password = binding.editPassSignUp.text.toString()
                )
            }
        }
    }

    private fun observer() {
        authViewModel.register.observe(viewLifecycleOwner) { state ->
            when(state) {
                UiState.Loading -> {
                    binding.signUpBtn.isClickable = false
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    findNavController().navigate(R.id.action_signUpFragment_to_listFragment)
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if(binding.editEmailSignUp.text.isNullOrEmpty()) {
            isValid = false
            toast("Email is empty.")
        } else {
            if (!binding.editEmailSignUp.text.toString().isValidEmail()) {
                isValid = false
                toast("Email is invalid.")
            }
        }

        if(binding.editPassSignUp.text.isNullOrEmpty()) {
            isValid = false
            toast("Password is empty.")
        } else {
            if (binding.editPassSignUp.text.toString().length < 8) {
                toast("Password must be at least 8 characters")
            }
        }
        return isValid
    }
}