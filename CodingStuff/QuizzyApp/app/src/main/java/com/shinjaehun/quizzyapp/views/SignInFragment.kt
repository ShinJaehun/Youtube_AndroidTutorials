package com.shinjaehun.quizzyapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shinjaehun.quizzyapp.R
import com.shinjaehun.quizzyapp.databinding.FragmentSignInBinding
import com.shinjaehun.quizzyapp.util.UiState
import com.shinjaehun.quizzyapp.util.isValidEmail
import com.shinjaehun.quizzyapp.util.toast
import com.shinjaehun.quizzyapp.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.signInBtn.setOnClickListener {
            if(validation()) {
                authViewModel.signIn(
                    email = binding.emailEditSignIN.text.toString(),
                    password = binding.passEditSignIn.text.toString()
                )
            }
        }

        binding.signUpText.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun observer() {
        authViewModel.login.observe(viewLifecycleOwner) { state ->
            when(state){
                UiState.Loading -> {
                    binding.signInBtn.isClickable = false
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    findNavController().navigate(R.id.action_signInFragment_to_listFragment)
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.emailEditSignIN.text.isNullOrEmpty()) {
            isValid = false
            toast("Email is empty.")
        } else {
            if (!binding.emailEditSignIN.text.toString().isValidEmail()) {
                isValid = false
                toast("Email is invalid.")
            }
        }
        if(binding.passEditSignIn.text.isNullOrEmpty()) {
            isValid = false
            toast("Password is empty.")
        } else {
            if (binding.passEditSignIn.text.toString().length < 8) {
                toast("Password must be at least 8 characters")
            }
        }
        return isValid
    }
}