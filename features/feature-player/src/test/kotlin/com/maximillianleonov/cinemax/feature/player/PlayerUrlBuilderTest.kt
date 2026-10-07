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

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayerUrlBuilderTest {
    private val builder = PlayerUrlBuilder("https://embed.vidrift.net")

    @Test
    fun movieUrl() {
        assertEquals(
            "https://embed.vidrift.net/embed/movie/1440098?title=Drawn%20Together",
            builder.movie(tmdbId = 1440098, title = "Drawn Together")
        )
    }

    @Test
    fun tvShowUrl() {
        assertEquals(
            "https://embed.vidrift.net/embed/tv/1399/1/1?title=Game%20of%20Thrones",
            builder.tvShow(tmdbId = 1399, season = 1, episode = 1, title = "Game of Thrones")
        )
    }

    @Test
    fun blankTitleIsOmitted() {
        assertEquals("https://embed.vidrift.net/embed/movie/5", builder.movie(5, title = "  "))
        assertEquals("https://embed.vidrift.net/embed/movie/5", builder.movie(5, title = null))
    }

    @Test
    fun titleIsPercentEncoded() {
        assertEquals(
            "https://embed.vidrift.net/embed/movie/1?title=Law%20%26%20Order",
            builder.movie(1, "Law & Order")
        )
        assertEquals(
            "https://embed.vidrift.net/embed/movie/1?title=Am%C3%A9lie",
            builder.movie(1, "Am\u00e9lie")
        )
        assertEquals(
            "https://embed.vidrift.net/embed/movie/1?title=What%27s%20Up%3F",
            builder.movie(1, "What's Up?")
        )
    }

    @Test
    fun trailingSlashInBaseUrlIsIgnored() {
        assertEquals(
            "https://embed.vidrift.net/embed/movie/7",
            PlayerUrlBuilder("https://embed.vidrift.net/").movie(7, null)
        )
    }

    @Test
    fun specialsSeasonZeroIsAllowed() {
        assertEquals(
            "https://embed.vidrift.net/embed/tv/1399/0/3",
            builder.tvShow(1399, season = 0, episode = 3, title = null)
        )
    }

    @Test
    fun optionsAreAppliedAndValidated() {
        val url = builder.movie(
            tmdbId = 1,
            title = "A",
            options = PlayerOptions(
                brand = "B".repeat(40),
                brandColorHex = "#00d1b2",
                showExit = true,
                hide = linkedSetOf(PlayerControl.Server, PlayerControl.Cast)
            )
        )
        assertEquals(
            "https://embed.vidrift.net/embed/movie/1?title=A&brand=${"B".repeat(28)}" +
                "&brandColor=00d1b2&exit=1&hide=server,cast",
            url
        )
        assertFalse(
            "brandColor=" in builder.movie(1, null, PlayerOptions(brandColorHex = "not-a-color"))
        )
    }

    @Test
    fun originIncludesNonDefaultPort() {
        assertEquals("https://embed.vidrift.net", builder.origin)
        assertEquals(
            "https://example.com:8443",
            PlayerUrlBuilder("https://example.com:8443/x").origin
        )
    }

    @Test
    fun invalidInputIsRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            PlayerUrlBuilder("http://embed.vidrift.net")
        }
        assertThrows(IllegalArgumentException::class.java) { PlayerUrlBuilder("not a url") }
        assertThrows(IllegalArgumentException::class.java) { builder.movie(0, null) }
        assertThrows(IllegalArgumentException::class.java) { builder.tvShow(1, 1, 0, null) }
    }

    @Test
    fun embedPageEscapesUrlAndOrigin() {
        val html = PlayerEmbedPage.html(
            embedUrl = "https://embed.vidrift.net/embed/movie/1?title=\"x\"&a=1",
            embedOrigin = "https://embed.vidrift.net"
        )
        assertTrue(
            "src=\"https://embed.vidrift.net/embed/movie/1?title=&quot;x&quot;&amp;a=1\"" in html
        )
        assertTrue("var ORIGIN = \"https://embed.vidrift.net\";" in html)
        assertFalse(" sandbox" in html)
    }
}
