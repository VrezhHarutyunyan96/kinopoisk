package com.example.kinopoisk.utils.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.*
import android.text.SpannableString
import android.text.style.*
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.CheckResult
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlin.math.abs


const val DRAWER_OPENED = 1
const val DRAWER_CLOSED = 2


fun View.spToPx(src: Int) = context.spToPx(src)

fun View.dpToPx(src: Int) = context.dpToPx(src)

fun View.dpToPx(src: Float) = context.dpToPx(src)

fun View.onClick(function: () -> Unit) {
    var previousClickTimestamp: Long = 0

    setOnClickListener {
        val currentTimeStamp = System.currentTimeMillis()
        val lastTimeStamp = previousClickTimestamp
        previousClickTimestamp = currentTimeStamp
        if (abs(currentTimeStamp - lastTimeStamp) > 300) {
            function()
        }
    }
}

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)


fun EditText.showKeyBoard() {
    requestFocus()
    val imm: InputMethodManager? =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.showToggleSoftKeyBoard() {
    requestFocus()
    val imm: InputMethodManager? =
        context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun EditText.hideKeyboard(activity: Activity) {
    val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideKeyBoard() {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.hideToggleSoftKeyBoard() {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0)
}

fun EditText.onCompleted(maxLength: Int = 5, body: (String) -> Unit) {
    addTextChangedListener {
        if (it.toString().length == maxLength)
            it?.toString()?.let { it1 -> body(it1) }
    }
}

fun DrawerLayout.onStateChanged(call: (Int) -> Unit) {
    addDrawerListener(object : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        }

        override fun onDrawerOpened(drawerView: View) {
            call(DRAWER_OPENED)
        }

        override fun onDrawerClosed(drawerView: View) {
            call(DRAWER_CLOSED)
        }

        override fun onDrawerStateChanged(newState: Int) {
        }
    })
}

fun ShapeAppearanceModel.photoMode(radius: Float) {
    toBuilder().setAllCornerSizes(radius).build()
}


fun ViewPager2.selected(call: (Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            call(position)
        }
    })
}

fun EditText.onChanged(delay: Long = 0, call: (String) -> Unit) {
    addTextChangedListener {
        delayed(delay) { call(it.toString()) }
    }
}

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun SpannableString.bold(start: Int, end: Int): SpannableString {
    this.setSpan(StyleSpan(Typeface.BOLD), start, end, 0)
    return this
}

fun SpannableString.color(color: String, start: Int, end: Int): SpannableString {
    this.setSpan(ForegroundColorSpan(Color.parseColor(color)), start, end, 0)
    return this
}
