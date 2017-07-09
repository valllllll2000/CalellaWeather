package com.vaxapp.calellaweather

import android.app.Activity
import android.content.Intent
import android.net.Uri

class Navigator {

    fun viewUrl(activity: Activity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }
}