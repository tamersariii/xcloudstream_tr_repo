package com.xcloudstream.FullHDFilmizlesene

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.Qualities
import org.jsoup.nodes.Element

class FullHDFilmizleseneProvider : MainAPI() {
    override var mainUrl = "https://www.fullhdfilmizlesene.now"
    override var name = "FullHDFilmizlesene"
    override val hasMainPage = true
    override var lang = "tr"
    override val supportedTypes = setOf(TvType.Movie)

    override val mainPage = mainPageOf(
        "" to "Son Eklenenler",
        "tur/aksiyon" to "Aksiyon",
        "tur/komedi" to "Komedi",
        "tur/dram" to "Dram"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val url = if (request.data.isEmpty()) "$mainUrl/page/$page" else "$mainUrl/${request.data}/page/$page"
        val document = app.get(url).document
        val items = document.select("div.film-box, article.film, div.film-item, li.film")
            .mapNotNull { it.toSearchResult() }
        return newHomePageResponse(request, items, hasNext = true)
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.selectFirst("h2 a, h3 a, .film-title, .title a")?.text()?.trim() ?: return null
        val href = fixUrl(this.selectFirst("a")?.attr("href") ?: return null)
        val poster = this.selectFirst("img")?.let {
            fixUrlNull(it.attr("src").ifEmpty { it.attr("data-src") })
        }

        return newMovieSearchResponse(title, href, TvType.Movie) {
            this.posterUrl = poster
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("$mainUrl/arama?q=$query").document
        return document.select("div.film-box, article.film, div.film-item, li.film")
            .mapNotNull { it.toSearchResult() }
    }

    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url).document
        val title = document.selectFirst("h1, .film-title")?.text()?.trim() ?: return null
        val poster = document.selectFirst("div.film-poster img, .poster img, .film-cover img")?.attr("src")
        val description = document.selectFirst("div.film-aciklama, .description, .ozet, .film-ozet")?.text()?.trim()
        val year = document.selectFirst("span.film-yil, .year, .film-year")?.text()?.trim()?.toIntOrNull()

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
        var found = false
        document.select("iframe").forEach { iframe ->
            val src = iframe.attr("src").ifEmpty { iframe.attr("data-src") }
            if (src.isNotEmpty()) {
                found = true
                callback.invoke(
                    newExtractorLink(
                        source = this.name,
                        name = "FullHDFilmizlesene",
                        url = fixUrl(src)
                    ) {
                        this.quality = Qualities.Unknown.value
                        this.referer = mainUrl
                    }
                )
            }
        }
        return found
    }
}
