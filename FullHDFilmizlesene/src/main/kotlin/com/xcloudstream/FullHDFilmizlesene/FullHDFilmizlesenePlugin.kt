package com.xcloudstream.FullHDFilmizlesene

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class FullHDFilmizlesenePlugin : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(FullHDFilmizleseneProvider())
    }
}