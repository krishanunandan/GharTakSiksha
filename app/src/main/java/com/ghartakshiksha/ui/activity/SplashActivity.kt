package com.ghartakshiksha.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ghartakshiksha.databinding.ActivitySplashBinding
import org.json.JSONObject

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        moveToNextScreen()
    }

    private fun moveToNextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)

            /* if (intent.extras != null) {
                 if (intent.hasExtra("google.delivered_priority")) {

                     if (intent.extras!!.getString("alldata") != null) {
                         Log.e("NotificationAllData-", "${intent.extras!!.getString("alldata")}")
                         val allDataJSON = intent.extras!!.getString("alldata")
                             ?.let { JSONObject(it) }
                         val dataJSON = allDataJSON?.getJSONObject("data")
                         val userType = dataJSON?.getString("fromusertype")
                         if (userType == "V") {
                             val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                             intent.putExtra("Notification", true)
                             intent.putExtra("NotificationData", dataJSON.toString())
                             startActivity(intent)
                             finishAffinity()

                         } else {
                             val intent =
                                 Intent(this@SplashActivity, HomeBakersProviderActivity::class.java)
                             intent.putExtra("Notification", true)
                             intent.putExtra("NotificationData", dataJSON.toString())
                             startActivity(intent)
                             finishAffinity()
                         }

                     }

                 } else {
                     moveNextScreen()
                 }
             } else {
                 moveNextScreen()
             }*/



            finish()
        }, 1000)
    }
}