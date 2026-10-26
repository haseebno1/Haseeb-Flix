# Internal reviewer notes — do not publish as listing copy

## Publication blockers

- **VidRift embed permission:** publisher-supplied email dated 2026-10-26 says embedding VidRift in HaseebFlix is allowed and free, subject to provider conditions (frame the embed, do not sandbox it, retain the public-embed ad, and use the embed URL rather than script-fetching it). See [`../docs/content-authorization.md`](../docs/content-authorization.md). Sender identity has not been independently authenticated.
- **Underlying content rights:** the same email says VidRift does not own or store media and indexes/relays publicly reachable sources. It does not provide title-level license evidence. Do not describe the email as a content-owner license or claim that public reachability proves distribution rights.
- **Publisher identity:** confirm legal publisher, support contact, website, privacy policy, target territories and store account eligibility.
- **Privacy:** publisher identity and contact are not confirmed; provider storage/ad network behavior and Android backup restoration need verification before a live policy and store declarations can be finalized.
- **Release artifact:** CI's optimized variant is debug-signed and uses placeholder TMDB key. It is not a production release. No release tag has been created.
- **TMDB:** source-to-build injection path exists, but live key behavior in a release artifact was not verified. Confirm applicable TMDB commercial/noncommercial terms, attribution and brand rules.
- **TMDB attribution gap:** the required notice is shown in the About/content-sources area in Settings. The approved official logo is still missing and must be added unaltered, smaller than HaseebFlix branding, before release.
- **Assets:** artwork and trademark/streaming screenshots need documented rights review.

## Claims supported by repository inspection

Package `com.haseebno1.haseebflix`; version 1.1.0/code 8; Android min API 23; catalog browsing/search/details/watchlist; third-party WebView playback; Room persistence; internet and network-state permissions. Treat all as repo-confirmed, not as proof of operational correctness or policy compliance.

## Placeholder register — resolve before console entry

`[PRIMARY_CATEGORY]`, `[MARKETING_URL]`, `[DISTRIBUTION_COUNTRIES]`, `[PRICE]`, `[AGE_RATING]`. Publisher display name and privacy contact are confirmed; the VidRift email is recorded, but it does not provide title-level licensing evidence.

Review note: the untracked parent guide is maintained at the user's requested location; this in-repository package is canonical for this branch pending publisher review.
