# HaseebFlix store submission package

**Status: filed to Orion Store.** This folder separates user-facing listing copy from internal review notes. It records repository evidence and current official platform references checked on **2026-10-26**. Eligibility for further stores is still evaluated per platform.

## Start here

- [Master listing](master-listing.md): editable console copy; replace every bracketed publisher placeholder before use.
- [Store metadata matrix](store-metadata-matrix.md): field lengths and platform-specific unknowns.
- [Asset checklist](asset-checklist.md): existing assets and missing rights/production evidence.
- [Privacy and data disclosures](privacy-and-data-disclosures.md): observed data paths and unresolved disclosures.
- [Privacy policy candidate](../privacy-policy.html): publisher identity and contact are confirmed; deploy it at the linked URL and verify it is publicly reachable before entering it in a store console.
- [VidRift embed authorization](../docs/content-authorization.md): publisher-supplied email and its scope/limits.
- [Review notes](review-notes.md): internal-only reviewer context, gates, and publisher actions.
- [Release readiness](release-readiness.md): build, signing, API-key and channel readiness.
- [Sources](sources.md): official sources, checked dates and limits.

## Current evidence snapshot

Repository branch `chore/haseebflix-branding-and-releases` is at `cf737a3182aff6846c296eb66134d4d8c66877f1`; package `com.haseebno1.haseebflix`, version `1.1.0` (code 8), min SDK 23, target/compile SDK 36. The source tree contains a third-party playback WebView, TMDB catalogue requests, Room storage, and the fastlane English metadata/assets. These are repository-confirmed, not independent audits of remote services.

GitHub Actions CI run [40261025705](https://github.com/haseebno1/Haseeb-Flix/actions/runs/40261025705) passed the debug build, optimized release-variant build, unit tests, and artifact uploads for commit `1067ef33922b4a527d91585e45a2c5745983fb00`. The optimized artifact is debug-signed and uses the CI placeholder TMDB key; it is not a production release. CI does not run lint, Detekt, or Spotless. The tagged-release workflow has since run: `v1.1.0` is published with a signed APK and a `.sha256` checksum. No claim is made that the public legacy release APK was inspected or works with TMDB.

The existing external guide remains at [`../HaseebFlix Store Guide.md`](../HaseebFlix%20Store%20Guide.md) and is maintained separately at the user's requested location.

## Gate before any publication

## Gate before further publication

Orion Store submission has been filed. Orion requires no rights evidence, so nothing is pending there. The gate below applies only to stores that ask for more.

The publisher supplied written confirmation from VidRift that embedding its player is allowed and free, subject to its embed conditions; see the authorization record. The email also says VidRift does not own or store the media, so title-level rights are not established by that email and remain unverified. That is a documentation gap, not a blocker.

For further stores: confirm the publisher's legal/display name and privacy contact, review provider storage/ad behavior, replace draft placeholders, and complete each store's current declarations.
