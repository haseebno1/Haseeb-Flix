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

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.webkit.WebViewCompat
import androidx.webkit.WebViewFeature

@SuppressLint("SetJavaScriptEnabled")
@Composable
@Suppress("LongMethod", "LongParameterList")
internal fun PlayerWebView(
    embedUrl: String,
    embedOrigin: String,
    hostPageOrigin: String,
    reloadKey: Int,
    onExit: () -> Unit,
    onError: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val fullscreenController = remember(context) { FullscreenController(context.findActivity()) }
    val currentEmbedUrl by rememberUpdatedState(embedUrl)
    val currentOnExit by rememberUpdatedState(onExit)
    val currentOnError by rememberUpdatedState(onError)
    var webView by remember { mutableStateOf<WebView?>(null) }

    // Leave the player's own fullscreen first, then the screen.
    BackHandler(enabled = fullscreenController.isActive) { fullscreenController.exit() }

    DisposableEffect(lifecycleOwner, webView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> webView?.onPause()
                Lifecycle.Event.ON_RESUME -> webView?.onResume()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            fullscreenController.exit()
        }
    }

    LaunchedEffect(webView, embedUrl, embedOrigin, hostPageOrigin, reloadKey) {
        webView?.loadDataWithBaseURL(
            "$hostPageOrigin/",
            PlayerEmbedPage.html(embedUrl = embedUrl, embedOrigin = embedOrigin),
            MimeType,
            Encoding,
            null
        )
    }

    AndroidView(
        modifier = modifier,
        factory = { viewContext ->
            WebView(viewContext).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setBackgroundColor(android.graphics.Color.BLACK)
                setLayerType(View.LAYER_TYPE_HARDWARE, null)
                android.webkit.CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    mediaPlaybackRequiresUserGesture = false
                    javaScriptCanOpenWindowsAutomatically = true
                    setSupportMultipleWindows(false)
                    allowFileAccess = true
                    allowContentAccess = true
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    userAgentString = userAgentString.replace("; wv", "").replace("Version/4.0 ", "")
                }
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        val url = request.url.toString()
                        val isHostPage = url == "$hostPageOrigin/" ||
                            url.startsWith("$hostPageOrigin/") ||
                            url == "about:blank"
                        return !isHostPage && request.isForMainFrame
                    }

                    override fun onReceivedError(
                        view: WebView,
                        request: WebResourceRequest,
                        error: WebResourceError
                    ) {
                        if (request.isForMainFrame || request.url.toString() == currentEmbedUrl) {
                            currentOnError()
                        }
                    }
                }
                webChromeClient = object : WebChromeClient() {
                    override fun onShowCustomView(view: View, callback: CustomViewCallback) =
                        fullscreenController.enter(view, callback)

                    override fun onHideCustomView() = fullscreenController.exit()
                }
                // Only the host page (not the framed player) receives this bridge.
                if (WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_LISTENER)) {
                    WebViewCompat.addWebMessageListener(
                        this,
                        PlayerEmbedPage.BridgeName,
                        setOf(hostPageOrigin)
                    ) { _, message, _, isMainFrame, _ ->
                        if (isMainFrame && PlayerMessage.parse(message.data) == PlayerMessage.Exit) {
                            currentOnExit()
                        }
                    }
                }
                webView = this
            }
        },
        onRelease = { view ->
            webView = null
            view.stopLoading()
            (view.parent as? ViewGroup)?.removeView(view)
            view.destroy()
        }
    )
}

/** Shows the player's fullscreen custom view above the app and restores everything on exit. */
private class FullscreenController(private val activity: Activity?) {
    var isActive by mutableStateOf(false)
        private set

    private var customView: View? = null
    private var callback: WebChromeClient.CustomViewCallback? = null
    private var previousOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    fun enter(view: View, viewCallback: WebChromeClient.CustomViewCallback) {
        val host = activity
        if (host == null || customView != null) {
            viewCallback.onCustomViewHidden()
            return
        }
        customView = view
        callback = viewCallback
        previousOrientation = host.requestedOrientation
        host.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        view.setBackgroundColor(android.graphics.Color.BLACK)
        val decorView = host.window.decorView as ViewGroup
        decorView.addView(
            view,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        view.bringToFront()
        view.requestFocus()
        view.visibility = View.VISIBLE
        host.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        insetsController(host).apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.systemBars())
        }
        isActive = true
    }

    fun exit() {
        val host = activity
        val view = customView
        val viewCallback = callback
        if (host == null || view == null) return
        // Clear state first: onCustomViewHidden() calls back into onHideCustomView().
        customView = null
        callback = null
        isActive = false

        (view.parent as? ViewGroup)?.removeView(view)
        insetsController(host).show(WindowInsetsCompat.Type.systemBars())
        host.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        host.requestedOrientation = previousOrientation
        viewCallback?.onCustomViewHidden()
    }

    private fun insetsController(host: Activity) =
        WindowCompat.getInsetsController(host.window, host.window.decorView)
}

private tailrec fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

private const val MimeType = "text/html"
private const val Encoding = "utf-8"
