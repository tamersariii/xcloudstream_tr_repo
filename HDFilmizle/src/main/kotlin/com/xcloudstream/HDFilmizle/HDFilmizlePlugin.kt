package com.xcloudstream.HDFilmizle

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class HDFilmizlePlugin : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(HDFilmizleProvider())
    }
}