package com.xcloudstream.HDFilmCehennemi

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import org.jsoup.nodes.Element

class HDFilmCehennemiProvider : MainAPI() {
    override var mainUrl = "https://www.hdfilmcehennemi.nl"
    override var name = "HDFilmCehennemi"
    override val hasMainPage = true
    override var lang = "tr"
    override val supportedTypes = setOf(TvType.Movie, TvType.TvSeries)

    override val mainPage = mainPageOf(
        "film" to "Filmler",
        "dizi" to "Diziler"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get("$mainUrl/${request.data}/sayfa/$page").document
        val items = document.select("article.film-box, article.dizi-box").mapNotNull { it.toSearchResult() }
        return newHomePageResponse(request, items, hasNext = true)
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.selectFirst("h2 a, h3 a")?.text() ?: return null
        val href = fixUrl(this.selectFirst("a")?.attr("href") ?: return null)
        val poster = this.selectFirst("img")?.let { fixUrlNull(it.attr("src")) }
        val type = if (href.contains("/dizi/")) TvType.TvSeries else TvType.Movie

        return if (type == TvType.Movie) {
            newMovieSearchResponse(title, href, type) { this.posterUrl = poster }
        } else {
            newTvSeriesSearchResponse(title, href, type) { this.posterUrl = poster }
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("$mainUrl/arama?q=$query").document
        return document.select("article.film-box, article.dizi-box").mapNotNull { it.toSearchResult() }
    }

    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url).document
        val title = document.selectFirst("h1")?.text() ?: return null
        val poster = document.selectFirst("div.film-poster img, div.dizi-poster img")?.attr("src")
        val description = document.selectFirst("div.film-aciklama, div.dizi-aciklama")?.text()
        val year = document.selectFirst("a.film-yil, span.film-yil")?.text()?.toIntOrNull()

        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = fixUrlNull(poster)
            this.plot = description
            this.year = year
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        document.select("iframe").forEach { iframe ->
            val src = iframe.attr("src")
            if (src.isNotEmpty()) {
                callback.invoke(
                    newExtractorLink(
                        source = this.name,
                        name = "HDFilmCehennemi",
                        url = fixUrl(src)
                    ) {
                        this.quality = Qualities.Unknown.value
                        this.referer = mainUrl
                    }
                )
            }
        }
        return true
    }
}