package com.ghartakshiksha.firebase


import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.ghartakshiksha.R
import com.ghartakshiksha.preferences.PreferenceProvider
import com.ghartakshiksha.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.util.*


class PushNotificationService : FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data["alldata"].orEmpty()
        /*val fromUserID = JSONObject(data).getJSONObject("data").getString("fromuserid")
        val type = message.data["type"]
        val activeChatUserID = PreferenceProvider(this).getValueByPreference("ActiveChatUserID")
        if (type == "" +
            "Chat") {
            if (fromUserID != activeChatUserID) {
                showNotification(message)
                broadcastNotification(message)
            }
        } else {*/
            showNotification(message)
            broadcastNotification(message)
      //  }
        Log.e("FirebaseData-", "${message.data.toString()}")
        Log.e("FirebaseNotification-", "${message.notification?.clickAction.toString()}")
        super.onMessageReceived(message)
    }

    private fun broadcastNotification(message: RemoteMessage) {
        val data = message.data["alldata"].orEmpty()
        val badgeCount = JSONObject(data).getJSONObject("data").getString("badge")
        val intent = Intent()
        intent.putExtra("NotificationType", message.data["type"])
        intent.putExtra("NotificationCount", "$badgeCount")
        intent.action = "Notification"
        sendBroadcast(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showNotification(remoteMessage: RemoteMessage) {
        var name = ""
        var message = ""
        val channelId = "asdf"//getString(R.string.notificationChannelID)
        val channelName = "asdf"//getString(R.string.notificationName)
        val data = remoteMessage.data["alldata"].orEmpty()
        val allDataJSON = JSONObject(data)
        val dataJSON = allDataJSON.getJSONObject("data")
        name = dataJSON.getString("name")
        message = dataJSON.getString("message")
        val userID = dataJSON.getString("fromuserid")
        val userType = dataJSON.getString("fromusertype")
        val fromUserProfileImage = dataJSON.getString("fromuserprofileimage")

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("ToUserID", userID)
        intent.putExtra("UserName", name)
        intent.putExtra("UserImage", fromUserProfileImage)
        intent.putExtra("ToUserType", userType)
        var parentIntent : Intent?=null


        if (userType == "C") {
            parentIntent = Intent(this, MainActivity::class.java)
        } else {
            parentIntent = Intent(this, MainActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP


        val notifyPendingIntent = PendingIntent.getActivities(
            this, 0, arrayOf(parentIntent,intent),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(this, channelId)
            .setContentIntent(notifyPendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(name)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setColor(ContextCompat.getColor(this, android.R.color.transparent))
            .setSound(defaultSoundUri)
            .setAutoCancel(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            builder.setChannelId(channelId)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = builder.build()
        val notificationId = Random().nextInt()
        notificationManager.notify(notificationId, notification)
    }

    override fun onNewToken(token: String) {
        PreferenceProvider(this).savedInPreference("FirebaseToken", token)
        super.onNewToken(token)
    }


    fun isAppInForeground(): Boolean {
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        return (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ||
                appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE)
    }
}