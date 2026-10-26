# Store metadata matrix

All lengths below are draft counts, including spaces and punctuation. Platform-specific limits were not assumed where current official documentation was unavailable or variable by locale/account. Recheck in the target console before submission. Verified 2026-10-26.

| Field | Draft | Count | Status |
|---|---|---:|---|
| App title | HaseebFlix | 10 | Repository/asset confirmed; store acceptance unverified |
| Short description | Discover films and series, build a watchlist, and watch. | 56 | Draft copy; measure against selected channel's live limit |
| Developer name | Abdul Haseeb | 12 | Publisher confirmed; use the same name in policy and app About |
| Package | `com.haseebno1.haseebflix` | — | Repo-confirmed |
| Version | 1.1.0 (8) | — | Repo-confirmed |
| Minimum Android | 6.0 / API 23 | — | Repo-confirmed |
| Support email | `abdulhaseebno1@gmail.com` | 24 | Publisher confirmed; use the same inbox in policy and app About |
| Website | `[MARKETING_URL]` | — | Publisher action required |
| Privacy policy | `https://haseebno1.github.io/Haseeb-Flix/privacy-policy.html` | — | Page is in this branch; verify public availability after deployment |
| Category / rating / price / countries | placeholders | — | Channel-specific publisher action |

## Checked channel-specific text constraints

- **F-Droid metadata:** its official quick-start asks for a short description under 80 characters with no trailing period, plus a changelog of at most 500 characters. Existing fastlane short description is now 64 characters, no final period; existing `8.txt` changelog is 298 characters excluding its line ending. No full-description length cap is stated in that guide.
- **Uptodown:** official publisher steps require a short description no longer than 70 characters and a full description of at least 50 words. Master short copy is 56 characters; master full description is 137 words. Existing icon is square and above 128 px, feature image is 1024×500. Eligibility remains blocked by unresolved rights/provider content, regardless of field lengths.
- For other channels, current field limits are not asserted unless an official source above documents them. Review the console for the selected language/market before entry.

## Channel readiness crosswalk

Listing completeness and publication eligibility are distinct. Current checks below are dated 2026-10-26; no channel submission is authorized or made.

| Channel | Status | Channel-specific issue / next evidence |
|---|---|---|
| Obtainium / GitHub releases | Conditionally ready for direct distribution only | Requires a verified developer-signed release; no curator acceptance. Existing public binary unverified. |
| “GitHub Store” client/index | Not verified | No GitHub-operated store/listing program or official eligibility docs found. |
| Orion Store | Conditionally ready | Public-repository/submission flow exists; exact review criteria and app acceptance not verified; resolve rights/artifact. |
| IzzyOnDroid | Blocked | FOSS/dependency, provider, artwork, maintainer/code-origin review and release signing; policy excludes apps facilitating unconsented copyrighted work. |
| Zapstore | Conditionally ready | `zsp` publishes release APK references; publisher needs signed APK and Nostr identity/certificate linking; rights gate remains. |
| Accrescent | Blocked | Official docs say new accounts are allowlist-only and new requests are not accepted; signed APK set and domain verification may apply. |
| F-Droid main repo | Blocked | Build-from-source, FLOSS dependency/toolchain, app key, attribution and IP review. F-Droid will include any API key in source and binary; key should not be treated as confidential. |
| Self-hosted F-Droid repo | Conditionally ready technically | Publisher must secure repo signing key/index and serve signed artifacts; it does not resolve app rights or app privacy. |
| APKPure | Not verified | Current official eligibility/metadata requirements and account state not established. |
| Uptodown | Blocked | Its current rules reject streaming apps offering paid third-party content free; actual provider catalog/rights unknown. Author/distribution-right holder must submit. |
| Aptoide Connect | Blocked | App ownership verification, signed APK, company profile, data disclosures and review required; docs say review requires active subscription. |
| Amazon Appstore, Android mobile | Not applicable | New mobile Android submissions ended February 2025; mobile store discontinued August 2025. |
| Amazon Appstore, Fire devices | Conditionally ready only after port/device review | Fire remains supported; test target devices and provide IP evidence/account details; app experience may not fit TV. |
| Samsung Galaxy Store | Blocked | Commercial seller status required; privacy URL, device tests, accurate metadata and third-party IP permissions needed. |
| Huawei AppGallery | Blocked | Publisher verification, Android package testing, metadata/assets/privacy URL and rights evidence required; account/region eligibility not verified. |

No Google Play or iOS submission materials are included. Recheck platform docs/console before action; no unsupported assertion of acceptance is made.
