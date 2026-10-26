# Privacy and data disclosures (evidence-based draft)

**Internal drafting aid, not a legal privacy policy or final store declaration.** Repository inspection date: 2026-10-26. Remote provider behavior and transitive SDK behavior are not fully verified.

## Observed in source

- Manifest requests `INTERNET` and `ACCESS_NETWORK_STATE`; application keeps `allowBackup="true"` and now points to backup rules for API 23–30 and API 31+. Those rules include the Room database only for cloud backup and device transfer. The watchlist and catalogue cache are included; other app files and preferences are excluded.
- Catalogue/search/details use TMDB API; image loading uses remote poster/backdrop images. A TMDB API key is sent as a request parameter through the app's network layer.
- Room stores catalogue/cache and watchlist data. No app account flow, SharedPreferences/DataStore use, or separately persisted tokens were found in the inspected source. The TMDB key is compiled into `BuildConfig`, so it is already extractable from the APK but is not an app-data backup item. Backup configuration intentionally preserves the database; it does not make the key secret.
- Playback loads a third-party WebView provider, default origin `https://embed.vidrift.net`, with title/TMDB identifiers in the constructed URL. The provider can receive IP/network metadata and requested title identifiers. Its page, cookies, scripts, redirects, advertising, trackers and onward sharing were not independently audited.
- In an email supplied by the publisher dated 2026-10-26, VidRift says public embeds include one ad and that the embedded player uses its own storage. This correspondence has not been independently authenticated; the exact storage types, ad recipients, retention and onward sharing remain unverified. Do not claim the player sets a specific cookie or uses a specific ad network without runtime evidence.
- The provider's public site says it indexes/relays publicly reachable media. The publisher-supplied email says VidRift does not own or store the media. These are provider statements, not title-level licensing evidence. No provider privacy policy or detailed collection/retention statement was found in its public documentation during this review.
- No direct analytics/advertising SDK was identified in the inspected app source. This is not a complete transitive dependency or runtime network audit.

## Must be investigated before declaration

1. Inspect dependency inventory/SBOM and run a complete code/runtime network audit.
2. Determine Android backup behavior for app data on supported devices and configure/describe it accurately.
3. Obtain provider privacy, cookie, tracker, retention and data-sharing statements; inspect runtime requests on a clean device, including the iframe's storage and ad requests.
4. Publisher identity and contact were confirmed by the account holder on 2026-10-26: Abdul Haseeb, abdulhaseebno1@gmail.com. A policy candidate is at `../privacy-policy.html`; deploy it at a stable URL and verify public access before store entry. Review jurisdiction-specific requirements and provider runtime behavior.
5. Complete each selected store's privacy/data-safety form based on evidence and legal advice where needed.

## Candidate plain-language policy copy (not published)

> HaseebFlix is an aggregator and does not host content; video playback is streamed from third-party providers. When you start playback, VidRift may receive your IP address, requested title identifiers and other connection details. VidRift says its embedded player uses its own storage and public embeds show one ad. HaseebFlix stores your watchlist and catalogue cache in the app database. Android cloud backup and device transfer include that database; other app files and preferences are excluded. The TMDB API key is compiled into the app and is not stored in backup data. The exact provider storage, ad recipients, tracking, retention and onward sharing have not been independently verified.

Publisher name and privacy email were supplied by the account holder on 2026-10-26. The public policy candidate is `../privacy-policy.html`; verify its availability after deployment and review jurisdiction-specific requirements and provider runtime behavior before store declarations.

**Do not claim:** no data leaves the device; no tracking; no ads; all data is local-only; or provider streams are authorized. Those statements are not established by repository inspection.
