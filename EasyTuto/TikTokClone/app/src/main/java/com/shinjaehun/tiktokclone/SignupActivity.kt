package com.shinjaehun.tiktokclone

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.shinjaehun.tiktokclone.databinding.ActivityMainBinding
import com.shinjaehun.tiktokclone.databinding.ActivitySignupBinding
import com.shinjaehun.tiktokclone.model.UserModel
import com.shinjaehun.tiktokclone.util.UiUtil

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            signup()
        }

        binding.goToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setInProgress(inProgress: Boolean) {
        if(inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.submitBtn.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.submitBtn.visibility = View.VISIBLE
        }
    }

    private fun signup() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val configmPassword = binding.confirmPasswordInput.text.toString()


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInput.setError("Email is not valid")
            return
        }

        if(password.length<6) {
            binding.passwordInput.setError("Minimum 6 characters")
            return
        }

        if (password != configmPassword) {
            binding.confirmPasswordInput.setError("Password is not matched")
            return
        }

        signupWithFirebase(email, password)

    }

    private fun signupWithFirebase(email: String, password: String) {
        setInProgress(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
//                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                it.user?.let { user ->
                    val userModel = UserModel(user.uid, email, email.substringBefore("@"))
                    Firebase.firestore.collection("tiktokclone_users")
                        .document(user.uid)
                        .set(userModel).addOnSuccessListener {
                            UiUtil.showToast(applicationContext, "Account created successfully")
                            setInProgress(false)
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                }
            }
            .addOnFailureListener {
                UiUtil.showToast(applicationContext, it.localizedMessage?: "Something went wrong")
                setInProgress(false)
            }
    }
}