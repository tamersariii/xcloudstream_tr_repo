package com.xcloudstream.HDFilmizle

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.ExtractorLinkType
import com.lagradost.cloudstream3.utils.Qualities
import org.jsoup.nodes.Element

class HDFilmizleProvider : MainAPI() {
    override var mainUrl = "https://www.hdfilmizle.vip"
    override var name = "HDFilmizle"
    override val hasMainPage = true
    override var lang = "tr"
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)

    override val mainPage = mainPageOf(
        "" to "Son Eklenenler",
        "film" to "Filmler",
        "dizi" to "Diziler"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val url = if (request.data.isEmpty()) "$mainUrl/page/$page" else "$mainUrl/${request.data}/page/$page"
        val document = app.get(url).document
        val items = document.select("div.film-box, article.film, div.dizi-box, .movie-card, .tv-card")
            .mapNotNull { it.toSearchResult() }
        return newHomePageResponse(request, items, hasNext = true)
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.selectFirst("h2 a, h3 a, .title a, .film-title, .dizi-title")?.text()?.trim() ?: return null
        val href = fixUrl(this.selectFirst("a")?.attr("href") ?: return null)
        val poster = this.selectFirst("img")?.let {
            fixUrlNull(it.attr("src").ifEmpty { it.attr("data-src") }.ifEmpty { it.attr("data-original") })
        }
        val type = if (href.contains("/dizi") || href.contains("/tv")) TvType.TvSeries else TvType.Movie

        return if (type == TvType.Movie) {
            newMovieSearchResponse(title, href, type) { this.posterUrl = poster }
        } else {
            newTvSeriesSearchResponse(title, href, type) { this.posterUrl = poster }
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("$mainUrl/arama?q=$query").document
        return document.select("div.film-box, article.film, div.dizi-box, .movie-card, .tv-card")
            .mapNotNull { it.toSearchResult() }
    }

    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url).document
        val title = document.selectFirst("h1, .film-title, .dizi-title")?.text()?.trim() ?: return null
        val poster = document.selectFirst("div.film-poster img, .poster img, .cover img")?.attr("src")
        val description = document.selectFirst("div.film-aciklama, .description, .ozet, .plot")?.text()?.trim()
        val year = document.selectFirst("span.film-yil, .year, .film-year")?.text()?.trim()?.toIntOrNull()
        val type = if (url.contains("/dizi") || url.contains("/tv")) TvType.TvSeries else TvType.Movie

        return if (type == TvType.Movie) {
            newMovieLoadResponse(title, url, type, url) {
                this.posterUrl = fixUrlNull(poster)
                this.plot = description
                this.year = year
            }
        } else {
            newTvSeriesLoadResponse(title, url, type, emptyList()) {
                this.posterUrl = fixUrlNull(poster)
                this.plot = description
                this.year = year
            }
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        var found = false
        document.select("iframe").forEach { iframe ->
            val src = iframe.attr("src").ifEmpty { iframe.attr("data-src") }
            if (src.isNotEmpty()) {
                found = true
                callback.invoke(
                    ExtractorLink(
                        source = this.name,
                        name = "HDFilmizle",
                        url = fixUrl(src),
                        referer = mainUrl,
                        quality = Qualities.Unknown.value,
                        type = ExtractorLinkType.VIDEO
                    )
                )
            }
        }
        return found
    }
}
