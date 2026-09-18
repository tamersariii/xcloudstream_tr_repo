package com.xcloudstream.UltraFilmizle

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class UltraFilmizlePlugin : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(UltraFilmizleProvider())
    }
}
