/*
 * Copyright 2022 Afig Aliyev
 * Modifications Copyright 2026 Abdul Haseeb
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

package com.haseebno1.haseebflix.feature.player

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayerNavigationPolicyTest {
    private val hostPageOrigin = "https://haseebflix.local"
    private val providerOrigin = "https://embed.vidrift.net"

    @Test
    fun allowsHostPageAndProviderOrigins() {
        assertTrue(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "https://haseebflix.local/",
                hostPageOrigin,
                providerOrigin
            )
        )
        assertTrue(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "https://embed.vidrift.net/embed/movie/5",
                hostPageOrigin,
                providerOrigin
            )
        )
        assertTrue(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "about:blank",
                hostPageOrigin,
                providerOrigin
            )
        )
    }

    @Test
    fun comparesOriginsInsteadOfUrlPrefixes() {
        assertFalse(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "https://embed.vidrift.net.evil.example/",
                hostPageOrigin,
                providerOrigin
            )
        )
        assertFalse(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "https://example.com/",
                hostPageOrigin,
                providerOrigin
            )
        )
    }

    @Test
    fun requiresHttpsAndTheConfiguredProviderPort() {
        assertFalse(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "http://embed.vidrift.net/",
                hostPageOrigin,
                providerOrigin
            )
        )
        assertFalse(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "https://embed.vidrift.net:8443/",
                hostPageOrigin,
                providerOrigin
            )
        )
        assertTrue(
            PlayerNavigationPolicy.isAllowedMainFrame(
                "https://embed.vidrift.net:8443/",
                hostPageOrigin,
                "https://embed.vidrift.net:8443"
            )
        )
    }
}
