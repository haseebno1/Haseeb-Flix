/*
 * Copyright 2022 Afig Aliyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maximillianleonov.cinemax.feature.player

import java.net.URI

/** Controls of the embedded player that can be hidden (`hide` URL parameter). */
internal enum class PlayerControl(val parameter: String) {
    Progress("progress"),
    Volume("volume"),
    Skip("skip"),
    Time("time"),
    Title("title"),
    Server("server"),
    Next("next"),
    Subtitles("subtitles"),
    Settings("settings"),
    Lock("lock"),
    Pip("pip"),
    Cast("cast"),
    Fullscreen("fullscreen")
}

internal data class PlayerOptions(
    val brand: String? = null,
    val brandColorHex: String? = null,
    val showExit: Boolean = false,
    val hide: Set<PlayerControl> = emptySet()
)

/**
 * Builds embed URLs:
 * - `{base}/embed/movie/{tmdbId}`
 * - `{base}/embed/tv/{tmdbId}/{season}/{episode}`
 * with optional, percent-encoded query parameters.
 */
internal class PlayerUrlBuilder(baseUrl: String) {
    private val base = baseUrl.trim().trimEnd('/')

    /** `scheme://host[:port]` of the provider. Used to validate messages from the player. */
    val origin: String

    init {
        val uri = runCatching { URI(base) }.getOrNull()
        require(uri != null && uri.scheme == "https" && !uri.host.isNullOrEmpty()) {
            "Player base URL must be a valid https URL: $baseUrl"
        }
        origin = "${uri.scheme}://${uri.authority}"
    }

    fun movie(tmdbId: Int, title: String?, options: PlayerOptions = PlayerOptions()): String {
        require(tmdbId > 0) { "Invalid TMDB id: $tmdbId" }
        return build(listOf(MoviePath, tmdbId.toString()), title, options)
    }

    fun tvShow(
        tmdbId: Int,
        season: Int,
        episode: Int,
        title: String?,
        options: PlayerOptions = PlayerOptions()
    ): String {
        require(tmdbId > 0) { "Invalid TMDB id: $tmdbId" }
        require(season >= 0) { "Invalid season: $season" }
        require(episode >= 1) { "Invalid episode: $episode" }
        return build(
            segments = listOf(TvPath, tmdbId.toString(), season.toString(), episode.toString()),
            title = title,
            options = options
        )
    }

    private fun build(segments: List<String>, title: String?, options: PlayerOptions): String {
        val query = buildList<Pair<String, String>> {
            title?.trim()?.takeIf { it.isNotEmpty() }?.let {
                add(TitleParameter to it.percentEncode())
            }
            options.brand?.trim()?.takeIf { it.isNotEmpty() }?.let {
                add(BrandParameter to it.take(MaxBrandLength).percentEncode())
            }
            options.brandColorHex?.removePrefix("#")?.takeIf { HexColorRegex.matches(it) }?.let {
                add(BrandColorParameter to it)
            }
            if (options.showExit) add(ExitParameter to "1")
            if (options.hide.isNotEmpty()) {
                add(HideParameter to options.hide.joinToString(",") { it.parameter })
            }
        }
        val path = (listOf(EmbedPath) + segments).joinToString("/")
        return buildString {
            append(base).append('/').append(path)
            if (query.isNotEmpty()) {
                append('?').append(query.joinToString("&") { (key, value) -> "$key=$value" })
            }
        }
    }
}

/** RFC 3986 percent-encoding: everything except unreserved characters is encoded as UTF-8. */
private fun String.percentEncode() = buildString {
    for (byte in this@percentEncode.toByteArray(Charsets.UTF_8)) {
        val value = byte.toInt() and ByteMask
        if (value.isUnreserved()) {
            append(value.toChar())
        } else {
            append('%').append(HexDigits[value shr HexShift]).append(HexDigits[value and HexMask])
        }
    }
}

private fun Int.isUnreserved() = this in 'a'.code..'z'.code ||
    this in 'A'.code..'Z'.code ||
    this in '0'.code..'9'.code ||
    this == '-'.code || this == '.'.code || this == '_'.code || this == '~'.code

private const val EmbedPath = "embed"
private const val MoviePath = "movie"
private const val TvPath = "tv"

private const val TitleParameter = "title"
private const val BrandParameter = "brand"
private const val BrandColorParameter = "brandColor"
private const val ExitParameter = "exit"
private const val HideParameter = "hide"

private const val MaxBrandLength = 28
private val HexColorRegex = Regex("^[0-9a-fA-F]{6}$")

private const val HexDigits = "0123456789ABCDEF"
private const val HexShift = 4
private const val HexMask = 0x0F
private const val ByteMask = 0xFF
