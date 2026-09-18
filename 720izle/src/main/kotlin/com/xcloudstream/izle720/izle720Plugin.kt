package com.xcloudstream.izle720

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class izle720Plugin : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(izle720Provider())
    }
}