package com.vaxapp.calellaweather

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v4.app.FragmentActivity

class Navigator {

    fun viewUrl(activity: FragmentActivity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }
}