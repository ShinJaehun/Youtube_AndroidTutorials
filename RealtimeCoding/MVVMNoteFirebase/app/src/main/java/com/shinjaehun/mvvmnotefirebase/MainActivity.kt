package com.shinjaehun.mvvmnotefirebase

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
    }
}