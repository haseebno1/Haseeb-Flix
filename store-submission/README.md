# HaseebFlix store submission package

**Status: draft; not submitted.** This folder separates user-facing listing copy from internal review notes. It records repository evidence and current official platform references checked on **2026-10-26**. It is not an assertion that the app is eligible for publication.

## Start here

- [Master listing](master-listing.md): editable console copy; replace every bracketed publisher placeholder before use.
- [Store metadata matrix](store-metadata-matrix.md): field lengths and platform-specific unknowns.
- [Asset checklist](asset-checklist.md): existing assets and missing rights/production evidence.
- [Privacy and data disclosures](privacy-and-data-disclosures.md): observed data paths and unresolved disclosures.
- [Review notes](review-notes.md): internal-only reviewer context, gates, and publisher actions.
- [Release readiness](release-readiness.md): build, signing, API-key and channel readiness.
- [Sources](sources.md): official sources, checked dates and limits.

## Current evidence snapshot

Repository branch `chore/haseebflix-branding-and-releases` is at `cf737a3182aff6846c296eb66134d4d8c66877f1`; package `com.haseebno1.haseebflix`, version `1.1.0` (code 8), min SDK 23, target/compile SDK 36. The source tree contains a third-party playback WebView, TMDB catalogue requests, Room storage, and the fastlane English metadata/assets. These are repository-confirmed, not independent audits of remote services.

GitHub Actions CI run [40251960296](https://github.com/haseebno1/Haseeb-Flix/actions/runs/40251960296) for this source commit passed its configured debug build, optimized release-variant build, `testDebugUnitTest`, and artifact uploads. It signs the optimized artifact with the debug key and injects `ci-placeholder` as the TMDB key. CI does not run lint, Detekt, or Spotless. The tagged-release workflow has not run; it requires signing secrets and a TMDB API key. No claim is made that the public legacy release APK was inspected or works with TMDB.

The existing external guide remains at [`../HaseebFlix Store Guide.md`](../HaseebFlix%20Store%20Guide.md) and is maintained separately at the user's requested location.

## Gate before any publication

Obtain documented rights/authorization for the content and playback provider; confirm publisher identity, support contact, privacy policy and target countries; review the provider's actual network/cookie behavior; replace draft placeholders; produce a properly signed release with a valid, terms-compliant TMDB key; then verify the final APK on a clean device and complete each store's current declarations. No store submission, remote companion repository, release tag or publication has been performed.
