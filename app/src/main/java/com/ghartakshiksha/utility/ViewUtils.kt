package com.ghartakshiksha.utility

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ghartakshiksha.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showMessageDialog(
    message: String,
    buttonTitle: String? = null,
    positiveButtonFunction: (() -> Unit)? = null
) {
    val dialog = Dialog(this, R.style.Theme_Dialog)
    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.message_dialog_view)
    val tvMessage = dialog.findViewById(R.id.tvMessage) as TextView
    val btnDialog = dialog.findViewById(R.id.btnDialog) as AppCompatButton

    tvMessage.text = message
    btnDialog.text = buttonTitle

    btnDialog.setOnClickListener {
        positiveButtonFunction?.invoke()
        dialog.dismiss()
    }
    dialog.show()
}

/*
fun Context.showTagExampleDialog(
) {
    val dialog = Dialog(this, R.style.Theme_Dialog)
    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.tag_example_layout)
    val btnDialog = dialog.findViewById(R.id.btnClose) as AppCompatButton

    btnDialog.setOnClickListener {
        dialog.dismiss()
    }
    dialog.show()
}

fun Context.doNotShowAgainDialog(
    positiveButtonFunction: (() -> Unit)? = null
) {
    val dialog = Dialog(this, R.style.Theme_Dialog)
    dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(true)
    dialog.setContentView(R.layout.do_not_show_again_dialog)
    val btnDialog = dialog.findViewById(R.id.btnDoNotShowAgain) as AppCompatButton

    btnDialog.setOnClickListener {
        positiveButtonFunction?.invoke()
        dialog.dismiss()
    }
    dialog.show()
}

*/



fun Context.isLocationPermissionGranted(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}


fun ProgressBar.hide() {
    visibility = View.GONE
}


fun Context.loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val drawable = CircularProgressDrawable(this)
       /* drawable.setColorSchemeColors(
            R.color.colorOne,
            R.color.colorOne
        )*/
        val color: Int = 0xFF333333.toInt()
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        drawable.centerRadius = 70f
        drawable.strokeWidth = 7f
        drawable.start()
        Glide.with(view)
            .load(url)
            .apply(RequestOptions().override(250, 250))
            .placeholder(drawable)
            .into(view)

    }
}

/*
@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
    if (strikeThrough) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}*/

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

