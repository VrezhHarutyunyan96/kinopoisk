package com.example.kinopoisk.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.kinopoisk.R
import com.example.kinopoisk.databinding.DialogErrorBinding

private var errorDialog: AlertDialog? = null

@SuppressLint("ClickableViewAccessibility")
fun Activity.errorDialog(message: String) {
    val viewGroup = findViewById<ViewGroup>(android.R.id.content);
    val binding = DialogErrorBinding.inflate(layoutInflater, viewGroup, false)
    val builder = AlertDialog.Builder(this, R.style.StandardDialogTheme)
    builder.setView(binding.root)
    if (errorDialog != null && errorDialog?.isShowing == true) {
        return
    }
    errorDialog = builder.create()
    errorDialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    errorDialog?.window?.setGravity(Gravity.BOTTOM)
    errorDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    errorDialog?.setCancelable(true)
    if (message.isNotEmpty()) binding.message.text = message
    binding.cursorLine.setOnTouchListener { view, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_MOVE) {
            errorDialog?.dismiss()
            view.performClick()
        }
        true
    }
    binding.imageError.setOnTouchListener { view, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_MOVE) {
            errorDialog?.dismiss()
            view.performClick()
        }
        true
    }
    errorDialog?.setOnDismissListener {
        errorDialog = null
    }
    errorDialog?.show()
}