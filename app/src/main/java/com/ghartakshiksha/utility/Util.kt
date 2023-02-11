package com.ghartakshiksha.utility


import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class Util {

    fun getSaleEndDaysTimeHours(saleEndDateTime: String): String {
        val patterns = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        patterns.timeZone = TimeZone.getTimeZone("UTC")
        val endDateTime = patterns.parse(saleEndDateTime)
        val currentDate = patterns.format(Date())
        val currentDateTime = patterns.parse(currentDate)
        var remainingHours = 0L
        var remainingMinute = 0L
        var remainingDays = 0L
        var difference: Long = currentDateTime?.time?.minus(endDateTime?.time ?: 0) ?: 0
        if (difference < 0) {
            difference = abs(difference)
            remainingHours = difference / (60 * 60 * 1000) % 24
            remainingMinute = difference / (60 * 1000) % 60
            remainingDays = difference / (24 * 60 * 60 * 1000)
        }

        return "$remainingDays,$remainingHours,$remainingMinute"
    }

    fun getDateTimeDifference(previousDateTime:String):String {
        val patterns = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH)
        patterns.timeZone = TimeZone.getTimeZone("UTC")
        val preDateTime = patterns.parse(previousDateTime)
        val currentDate = patterns.format(Date())
        val currentDateTime = patterns.parse(currentDate)
        var remainingHours = 0L
        var remainingMinute = 0L
        var difference: Long = currentDateTime?.time?.minus(preDateTime?.time ?: 0) ?: 0
        if (difference > 0) {
            difference = abs(difference)
            remainingHours = difference / (60 * 60 * 1000) % 24
            remainingMinute = difference / (60 * 1000) % 60
        }

        return "$remainingMinute"
    }

    fun setStatusBarColor(context: Context?, window: Window, colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(context!!, colorId)
    }


}