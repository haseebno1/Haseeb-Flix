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

/**
 * Tiny local HTML page that frames the embed player in an `<iframe>` (as the provider requires:
 * no `sandbox`, default referrer policy) and forwards the player's `postMessage` events to
 * the app through [BridgeName].
 */
internal object PlayerEmbedPage {
    const val BridgeName = "cinemaxBridge"

    fun html(embedUrl: String, embedOrigin: String) = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <style>
          * { margin: 0; padding: 0; box-sizing: border-box; }
          html, body {
            width: 100vw;
            height: 100vh;
            margin: 0;
            padding: 0;
            background: #000;
            overflow: hidden;
          }
          iframe {
            position: fixed;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100vh;
            border: 0;
            outline: 0;
          }
        </style>
        </head>
        <body>
        <iframe id="player" src="${embedUrl.escapeHtml()}"
          allow="autoplay *; fullscreen *; picture-in-picture *; encrypted-media *; accelerometer; gyroscope"
          allowfullscreen referrerpolicy="strict-origin-when-cross-origin"></iframe>
        <script>
        (function () {
          var ORIGIN = ${embedOrigin.asJsString()};
          var frame = document.getElementById('player');
          window.addEventListener('message', function (e) {
            if (e.origin !== ORIGIN || !e.data || typeof e.data.type !== 'string') return;
            if (window.$BridgeName) window.$BridgeName.postMessage(JSON.stringify(e.data));
          });
          window.cinemaxSend = function (message) {
            frame.contentWindow.postMessage(message, ORIGIN);
          };
        })();
        </script>
        </body>
        </html>
    """.trimIndent()
}

internal fun String.escapeHtml() = buildString {
    for (char in this@escapeHtml) {
        when (char) {
            '&' -> append("&amp;")
            '<' -> append("&lt;")
            '>' -> append("&gt;")
            '"' -> append("&quot;")
            '\'' -> append("&#39;")
            else -> append(char)
        }
    }
}

internal fun String.asJsString() = buildString {
    append('"')
    for (char in this@asJsString) {
        when (char) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '<' -> append("\\u003C")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            else -> append(char)
        }
    }
    append('"')
}
