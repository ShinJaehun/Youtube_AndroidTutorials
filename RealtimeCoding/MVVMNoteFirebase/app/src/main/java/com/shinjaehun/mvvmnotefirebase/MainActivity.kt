package com.shinjaehun.mvvmnotefirebase

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        val user: MutableMap<String, Any> = HashMap()
//        user["first"] = "Jaehun"
//        user["last"] = "Shin"
//        user["born"] = 1978
//
//
//        FirebaseFirestore.getInstance().collection("users")
//            .add(user)
//            .addOnSuccessListener { documnetReference ->
//                Log.i(TAG, "documentReference: $documnetReference")
//            }
//            .addOnFailureListener {
//                Log.i(TAG, "Error~~")
//            }

        // 이거 안 되는데...
//        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
//        navController = navHostFragment.navController

    }

    // 얘는 deprecated고...
//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        if (navController.currentDestination?.id == R.id.loginFragment) {
//            moveTaskToBack(true)
//        } else {
//            super.onBackPressed()
//        }
//    }

    // 이거 효과 없는데
//    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
//        override fun handleOnBackPressed() {
//            if (navController.currentDestination?.id == R.id.loginFragment) {
//                moveTaskToBack(true)
//            } else {
//                this.isEnabled = false
//                onBackPressedDispatcher.onBackPressed()
//            }
//        }
//    }
}