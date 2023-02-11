package com.ghartakshiksha.utility

import android.util.Log
import androidx.lifecycle.*

class AppLifecycleListener : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            Log.e("HomeBakerStatus", "Foreground")
        } else if (event == Lifecycle.Event.ON_STOP) {
            Log.e("HomeBakerStatus", "Background")
        }
    }
}
