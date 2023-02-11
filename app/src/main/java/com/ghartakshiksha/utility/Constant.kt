package com.ghartakshiksha.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityCompat
import com.ghartakshiksha.network.model.AddressComponents
import java.util.*

object Constant {
    const val appId = "23449137-9d2a-4357-bb23-e905a8edb6aa"
    const val appKey = "21526ccf-5a86-4fae-8d0f-855ed76c97dd"
    const val noInternetConnection =
        "No internet Connection. \nReload after connecting to strong network."
    const val howItWork = "https://www.homebakers.app/home/index#dvHowToWorks"
    const val whyJoin = "https://www.homebakers.app/home/index#whyjoin"
    const val privacyPolicy = "https://www.homebakers.app/home/privacypolicy"
    const val aboutUs = "https://www.homebakers.app/home/aboutus"
    const val exampleItemImageURL =
        "https://storage.googleapis.com/homebakers-64ef4.appspot.com/HBExamples/IMAGE.jpg"
    const val exampleItemVideoURL =
        "https://storage.googleapis.com/homebakers-64ef4.appspot.com/HBExamples/VIDEO.mp4"

    const val exampleInActionVideoURL =
        "https://storage.googleapis.com/homebakers-64ef4.appspot.com/HBExamples/Video2.mp4"
    const val bucketName = "gs://homebakers-64ef4.appspot.com"
    const val foodSafetyUrl = "https://www.homebakers.app/home/foodsafety"
    const val SEARCH_RESULT_CODE = 101
    const val LOCATION_RESULT_CODE = 102
    const val ITEM_RESULT_CODE = 103
    const val SEND = 0
    const val RECEIVE = 1

    const val MIN_BUFFER_DURATION = 3000
    const val MAX_BUFFER_DURATION = 6000
    const val MIN_PLAYBACK_RESUME_BUFFER = 1500
    const val MIN_PLAYBACK_START_BUFFER = 3000

    fun getState(): ArrayList<String> {
        val arrayList: ArrayList<String> = ArrayList<String>()
        arrayList.add("Select State")
        arrayList.add("Alabama")
        arrayList.add("Alaska")
        arrayList.add("Arizona")
        arrayList.add("Arkansas")
        arrayList.add("California")
        arrayList.add("Colorado")
        arrayList.add("Connecticut")
        arrayList.add("Delaware")
        arrayList.add("Florida")
        arrayList.add("Georgia")
        arrayList.add("Hawaii")
        arrayList.add("Idaho")
        arrayList.add("Illinois")
        arrayList.add("Indiana")
        arrayList.add("Iowa")
        arrayList.add("Kansas")
        arrayList.add("Kentucky")
        arrayList.add("Louisiana")
        arrayList.add("Maine")
        arrayList.add("Maryland")
        arrayList.add("Massachusetts")
        arrayList.add("Michigan")
        arrayList.add("Minnesota")
        arrayList.add("Mississippi")
        arrayList.add("Missouri")
        arrayList.add("Montana")
        arrayList.add("Nebraska")
        arrayList.add("Nevada")
        arrayList.add("New Hampshire")
        arrayList.add("New Jersey")
        arrayList.add("New Mexico")
        arrayList.add("New York")
        arrayList.add("North Carolina")
        arrayList.add("North Dakota")
        arrayList.add("Ohio")
        arrayList.add("Oklahoma")
        arrayList.add("Oregon")
        arrayList.add("Pennsylvania")
        arrayList.add("Rhode Island")
        arrayList.add("South Carolina")
        arrayList.add("South Dakota")
        arrayList.add("Tennessee")
        arrayList.add("Texas")
        arrayList.add("Utah")
        arrayList.add("Vermont")
        arrayList.add("Virginia")
        arrayList.add("Washington")
        arrayList.add("West Virginia")
        arrayList.add("Wisconsin")
        arrayList.add("Wyoming")

        return arrayList
    }

    fun getTakingOrdersCount(): ArrayList<String> {
        val arrayList: ArrayList<String> = ArrayList<String>()
        arrayList.add("1 day")
        arrayList.add("2 days")
        arrayList.add("3 days")
        arrayList.add("4 days")
        arrayList.add("5 days")
        return arrayList
    }


    fun getFilterOptions(): ArrayList<String> {
        val arrayList: ArrayList<String> = ArrayList<String>()
        arrayList.add("No Filter")
        arrayList.add("Active")
        arrayList.add("Inactive")
        arrayList.add("In Review")
        return arrayList
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    var LOCATION_PERMISSIONS =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    var STORAGE_PERMISSIONS =
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    fun hasPermissions(context: Context?, vararg permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    permission.toString()
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }

        return true
    }


    fun getCityAndState(arraylist: ArrayList<AddressComponents>): String {
        val addressBuilder = StringBuilder()
        for (addressComponent in arraylist) {
            val type = addressComponent.types
            if (type.size > 0) {
                if (type[0] == "locality") {
                    addressBuilder.append("${addressComponent.longName}, ")
                } else if (type[0] == "administrative_area_level_1") {
                    addressBuilder.append(addressComponent.longName)
                }
            }
        }
        return addressBuilder.toString()
    }


    fun getYear(): ArrayList<String> {

        val year = ArrayList<String>()
        val cal = Calendar.getInstance()
        val yearOne = cal[Calendar.YEAR]
        val yearTwo = cal[Calendar.YEAR] - 1
        val yearThree = cal[Calendar.YEAR] - 2
        val yearFour = cal[Calendar.YEAR] - 3
        val yearFive = cal[Calendar.YEAR] - 4

        year.add("$yearOne")
        year.add("$yearTwo")
        year.add("$yearThree")
        year.add("$yearFour")
        year.add("$yearFive")

        return year
    }

    fun setAmount(amount: String): String {
        var actualAmount = ""
        val splitAmount = amount.split(".")
        val partOne = splitAmount[1].substring(0, 2)
        actualAmount = if (partOne == "00") {
            splitAmount[0]
        } else {
            amount
        }
        return actualAmount
    }

    fun getMonthName(monthID: Int): String {
        var month = ""
        when (monthID) {
            1 -> {
                month = "JAN"
            }
            2 -> {
                month = "FEB"
            }
            3 -> {
                month = "MAR"
            }
            4 -> {
                month = "APR"
            }
            5 -> {
                month = "MAY"
            }
            6 -> {
                month = "JUN"
            }
            7 -> {
                month = "JUL"
            }
            8 -> {
                month = "AUG"
            }
            9 -> {
                month = "SEP"
            }
            10 -> {
                month = "OCT"
            }
            11 -> {
                month = "NOV"
            }
            12 -> {
                month = "DEC"
            }
        }
        return month
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        if (dpValue <= 0) return 0
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Float {
        if (spValue <= 0) return 0F
        val scale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return spValue * scale
    }

    fun formatNum(time: Int): String {
        return if (time < 10) "0$time" else time.toString()
    }

    fun formatMillisecond(millisecond: Int): String {
        val retMillisecondStr: String
        retMillisecondStr = if (millisecond > 99) {
            (millisecond / 10).toString()
        } else if (millisecond <= 9) {
            "0$millisecond"
        } else {
            millisecond.toString()
        }
        return retMillisecondStr
    }
}