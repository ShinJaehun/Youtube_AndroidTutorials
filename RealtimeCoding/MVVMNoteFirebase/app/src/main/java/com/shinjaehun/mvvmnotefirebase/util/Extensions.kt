package com.shinjaehun.mvvmnotefirebase.util

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.shinjaehun.mvvmnotefirebase.R

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun ChipGroup.addChip(
    text: String,
    isTouchTargetSize: Boolean = false,
    closeIconListener: View.OnClickListener? = null
) {
    val chip: Chip = LayoutInflater.from(context).inflate(R.layout.item_chip, null, false) as Chip
    chip.text = if (text.length > 9) text.substring(0,9) + "..." else text
    chip.isCheckable = false
    chip.setEnsureMinTouchTargetSize(isTouchTargetSize)
    if (closeIconListener != null) {
       chip.closeIcon = ContextCompat.getDrawable(context, com.google.android.material.R.drawable.ic_mtrl_chip_close_circle)
       chip.isCloseIconVisible = true
       chip.setOnCloseIconClickListener(closeIconListener)
    }
    addView(chip)
}

fun Context.createDialog(layout: Int, cancelable: Boolean): Dialog {
    val dialog = Dialog(this, android.R.style.Theme_Dialog)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(layout)
    dialog.window?.setGravity(Gravity.CENTER)
    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(cancelable)
    return dialog
}

val Int.dpToPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxToDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun String.isValidEmail() =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()