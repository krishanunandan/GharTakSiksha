package com.ghartakshiksha.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.location.Location
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.DialogFragment
import com.ghartakshiksha.R
import com.ghartakshiksha.preferences.PreferenceProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.io.File
import java.io.FileNotFoundException
import java.util.*


fun Activity.makeStatusBarTransparent() {
    window.apply {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        statusBarColor = Color.TRANSPARENT
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

fun Activity.setStatusBarColorAndAppearance(statusBarColor: String, isLight: Boolean) {
    try {
        window.statusBarColor = (Color.parseColor(statusBarColor))
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.setStatusBarColor(color: Int) {
    var flags = window?.decorView?.systemUiVisibility // get current flag
    if (flags != null) {
        if (isColorDark(color)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window?.decorView?.systemUiVisibility = flags
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            window?.decorView?.systemUiVisibility = flags
        }
    }
    window?.statusBarColor = color
}

fun Activity.isColorDark(color: Int): Boolean {
    val darkness =
        1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
    return darkness >= 0.5
}


fun Context.isLoggedIn(): Boolean {
    return PreferenceProvider(this).getBooleanToSF("isLoggedIn")
}

fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    dialog?.setCanceledOnTouchOutside(false)
}

fun DialogFragment.setWidthPercentWithCancellable(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    dialog?.setCanceledOnTouchOutside(true)
}


fun <T : View> T.height(function: (Int) -> Unit) {
    if (height == 0)
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                function(height)
            }
        })
    else function(height)
}


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

@SuppressLint("MissingPermission")
fun Context.getCurrentLatLong(): String {
    var latlong = ""
    val mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    mFusedLocationProviderClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
        .addOnSuccessListener { location: Location? ->
            if (location == null)
                Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
            else {
                latlong = "${location.latitude},${location.longitude}"
                PreferenceProvider(this).savedInPreference(
                    "Latitude",
                    "${location.latitude}"
                )
                PreferenceProvider(this).savedInPreference(
                    "Longitude",
                    "${location.longitude}"
                )
            }
        }
    return latlong
}

fun ImageView.setBitmap(videoUrl: String) {

    Coroutines.io {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(videoUrl, HashMap<String, String>())
        val bitmap = retriever.getFrameAtTime(0, OPTION_CLOSEST_SYNC)

        Coroutines.main {
            this.setImageBitmap(bitmap)
        }

    }
}

fun ImageView.setImage(url: String, progressBar: ProgressBar) {
    try {

        progressBar.visibility = View.VISIBLE
        if (url.isNotEmpty()) {
            val requestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(this.context)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).error(R.drawable.ic_launcher_foreground)
                .into(this)
        }
    } catch (exception: FileNotFoundException) {
        Log.e("Exception V", "${exception.message}")
    }
}

fun ImageView.setProfileImage(url: String, progressBar: ProgressBar) {
    try {
        progressBar.visibility = View.VISIBLE
        if (url.isNotEmpty()) {
            val requestOptions = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)

            Glide.with(this.context)
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                }).error(R.drawable.ic_launcher_foreground)
                .into(this)
        }
    } catch (exception: FileNotFoundException) {
        Log.e("Exception V", "${exception.message}")
    }
}

fun ImageView.setImageFromVideo(url: String) {
    try {
        val cropOptions = RequestOptions()

        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    } catch (exception: FileNotFoundException) {
        Log.e("Exception V", "${exception.message}")
    }
}

fun ProgressBar.setProgressColor() {
    val color: Int = 0xFFC0643D.toInt()
    this.indeterminateDrawable.colorFilter =
        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            color,
            BlendModeCompat.SRC_ATOP
        )
}


fun Uri.getFileNameWithExtension(context: Context): String? {
    val url = this.path?.let { path -> File(path).name }.orEmpty()
    val fileExtension = MimeTypeMap.getFileExtensionFromUrl(url)
    return if (fileExtension.isNotEmpty()) ".$fileExtension" else null
}

fun Uri.getMimeType(context: Context): String? {
    return when (scheme) {
        ContentResolver.SCHEME_CONTENT -> context.contentResolver.getType(this)
        ContentResolver.SCHEME_FILE -> MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            MimeTypeMap.getFileExtensionFromUrl(toString()).toLowerCase(Locale.US)
        )
        else -> null
    }
}

fun Context.getVideoLength(uri: Uri): Long {
    val retriever = MediaMetadataRetriever()
    retriever.setDataSource(this, uri)
    val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
    val timeInMilliSec = time!!.toLong()
    val timeInSec = timeInMilliSec / 1000
    Log.e("Video Duration -", "$timeInSec")

    return timeInSec
}


fun Activity.logout() {
    //val lastLatitude = PreferenceProvider(this).getValueByPreference("Latitude")
    //val lastLongitude = PreferenceProvider(this).getValueByPreference("Longitude")
    PreferenceProvider(this).clearPreference()
    //PreferenceProvider(this).savedInPreference("Latitude", "$lastLatitude")
    //PreferenceProvider(this).savedInPreference("Longitude", "$lastLongitude")

    /* PreferenceProvider(this).saveIntToSF("CustomerID", 0)
    PreferenceProvider(this).saveIntToSF("VendorID", 0)
    PreferenceProvider(this).savedInPreference("CustomerName", "")
    PreferenceProvider(this).savedInPreference("UserType", "")
    PreferenceProvider(this).savedInPreference("CustomerEmail", "")
    PreferenceProvider(this).saveBooleanToSF("isLoggedIn", false)
    PreferenceProvider(this).savedInPreference("FirebaseToken", "")*/
}


