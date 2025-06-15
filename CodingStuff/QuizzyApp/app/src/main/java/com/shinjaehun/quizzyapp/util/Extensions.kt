package com.shinjaehun.quizzyapp.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil3.ImageDrawable
import coil3.load

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}


fun ImageView.loadImageFromUrl(url: String) {
    this.load(url)
}

fun ImageView.loadImageFromUri(uri: Uri) {
    this.setImageURI(uri)
}

fun ImageView.loadImageFromDrawable(resId: Int){
    this.setImageResource(resId)
}
