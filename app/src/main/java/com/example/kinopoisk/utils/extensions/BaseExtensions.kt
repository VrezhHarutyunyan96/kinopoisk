package com.example.kinopoisk.utils.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Base64
import android.util.TypedValue
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.koin.core.context.KoinContextHandler


internal fun runOnUiThread(action: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        action()
        return
    }

    val handler = Handler(Looper.getMainLooper())
    handler.post(action)
}

fun Context.spToPx(src: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, src.toFloat(), resources.displayMetrics)
            .toInt()
}

fun Context.dpToPx(src: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, src.toFloat(), resources.displayMetrics)
            .toInt()
}


fun Context.getFitSystemWindowSize(root: View) = if (root.fitsSystemWindows) {
    val resources: Resources = resources
    val resourceId: Int = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    resources.getDimensionPixelSize(resourceId)
} else 0

fun Context.dpToPx(src: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, src, resources.displayMetrics)
}

fun Activity.string(@StringRes resId: Int): String {
    return getString(resId)
}

fun color(@ColorRes resId: Int): Int {
    val app = KoinContextHandler.get().get<Application>()
    return ContextCompat.getColor(app, resId)
}

fun drawable(@DrawableRes resId: Int): Drawable? {
    val app = KoinContextHandler.get().get<Application>()
    return ContextCompat.getDrawable(app, resId)
}

fun delayed(milliseconds: Long, body: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(body, milliseconds)
}
