# Privacy and data disclosures (evidence-based draft)

**Internal drafting aid, not a legal privacy policy or final store declaration.** Repository inspection date: 2026-10-26. Remote provider behavior and transitive SDK behavior are not fully verified.

## Observed in source

- Manifest requests `INTERNET` and `ACCESS_NETWORK_STATE`; application keeps `allowBackup="true"` and now points to backup rules for API 23–30 and API 31+. Those rules include the Room database only for cloud backup and device transfer. The watchlist and catalogue cache are included; other app files and preferences are excluded.
- Catalogue/search/details use TMDB API; image loading uses remote poster/backdrop images. A TMDB API key is sent as a request parameter through the app's network layer.
- Room stores catalogue/cache and watchlist data. No app account flow, SharedPreferences/DataStore use, or separately persisted tokens were found in the inspected source. The TMDB key is compiled into `BuildConfig`, so it is already extractable from the APK but is not an app-data backup item. Backup configuration intentionally preserves the database; it does not make the key secret.
- Playback loads a third-party WebView provider, default origin `https://embed.vidrift.net`, with title/TMDB identifiers in the constructed URL. The provider can receive IP/network metadata and requested title identifiers. Its page, cookies, scripts, redirects, advertising, trackers and onward sharing were not independently audited.
- The provider's public site says it indexes/relays publicly reachable media and that public embeds carry an ad. This is the provider's own claim, not evidence that any individual stream is licensed. No provider privacy policy or detailed collection/retention statement was found in its public documentation during this review.
- No direct analytics/advertising SDK was identified in the inspected app source. This is not a complete transitive dependency or runtime network audit.

## Must be investigated before declaration

1. Inspect dependency inventory/SBOM and run a complete code/runtime network audit.
2. Determine Android backup behavior for app data on supported devices and configure/describe it accurately.
3. Obtain provider privacy, cookie, tracker, retention and data-sharing statements; inspect runtime requests on a clean device.
4. Publish a real policy at a stable URL covering TMDB, image CDN, playback provider, diagnostics if any, local storage/backup, retention, contact and user rights for the chosen jurisdictions.
5. Complete each selected store's privacy/data-safety form based on evidence and legal advice where needed.

## Candidate plain-language policy copy (not published)

> HaseebFlix is an aggregator and does not host content; video playback is streamed from third-party providers. When you start playback, the provider may receive your IP address, requested title identifiers and other connection details, and its player may show its own advertising. HaseebFlix stores your watchlist in app data. Android cloud backup and device transfer include the app database, which contains the watchlist and catalogue cache; other app files and preferences are excluded. The TMDB API key is compiled into the app and is not stored in backup data. Provider cookies, tracking, retention and onward sharing have not been independently verified.

This draft still needs publisher identity/contact, jurisdiction-specific language, and verification of provider runtime behavior. Do not make it public until those details are reviewed.

**Do not claim:** no data leaves the device; no tracking; no ads; all data is local-only; or provider streams are authorized. Those statements are not established by repository inspection.
