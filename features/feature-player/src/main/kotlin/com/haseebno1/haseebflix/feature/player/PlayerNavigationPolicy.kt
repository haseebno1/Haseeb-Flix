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

import java.net.URI
import java.util.Locale

internal object PlayerNavigationPolicy {
    fun isAllowedMainFrame(
        url: String,
        hostPageOrigin: String,
        providerOrigin: String
    ): Boolean {
        if (url == "about:blank") return true

        val requestedOrigin = httpsOrigin(url) ?: return false
        return requestedOrigin == httpsOrigin(hostPageOrigin) ||
            requestedOrigin == httpsOrigin(providerOrigin)
    }

    private fun httpsOrigin(value: String): String? {
        val uri = runCatching { URI(value) }.getOrNull() ?: return null
        if (!uri.scheme.equals("https", ignoreCase = true) || uri.userInfo != null) return null
        val host = uri.host?.lowercase(Locale.ROOT) ?: return null
        val port = uri.port.takeIf { it != DefaultHttpsPort }
        return if (port == null || port == -1) {
            "https://$host"
        } else {
            "https://$host:$port"
        }
    }

    private const val DefaultHttpsPort = 443
}
