# Sources and verification log

Checked 2026-10-26. Official docs may change; recheck immediately before submission. “Not verified” means the source or account-specific condition was not independently confirmed.

## Repository sources

- `app/build.gradle.kts`, manifest, `NetworkModule.kt`, `ApiKeyProviderConventionPlugin.kt`, player sources, Room modules, `.github/workflows/ci.yml`, `.github/workflows/release.yml`, fastlane metadata/assets. Local tree at `cf737a3182aff6846c296eb66134d4d8c66877f1`.
- GitHub Actions PR build/test state was previously observed green for the branch. The precise run and resulting binary were not re-fetched as part of this document pass; recheck before relying on it.

## Platform/service primary sources

| Channel/topic | Official reference | Finding / limit |
|---|---|---|
| TMDB API terms and attribution | [TMDB API Terms of Use](https://www.themoviedb.org/api-terms-of-use), [TMDB logos and attribution](https://www.themoviedb.org/about/logos-attribution), [TMDB API FAQ](https://developer.themoviedb.org/docs/faq) | Terms require an approved TMDB logo and prominent disclaimer in the app; FAQ says noncommercial use requires attribution and commercial use requires contacting TMDB. Publisher must establish applicable status. |
| Android backup behavior | [Android Auto Backup docs](https://developer.android.com/identity/data/autobackup) | App now uses legacy and API 31+ rules to include only its Room database for cloud backup/device transfer; verify built manifest and actual restore on supported OS versions. |
| Playback provider | [VidRift public embed page](https://vidrift.net/) | Provider says it indexes/relays publicly reachable media and public embeds carry an ad. This self-description is not proof of licensing or a full privacy statement; no detailed privacy page was found in public docs. |
| Obtainium/GitHub releases | [Obtainium project](https://github.com/ImranR98/Obtainium), [GitHub releases](https://docs.github.com/en/repositories/releasing-projects-on-github/about-releases) | Direct release distribution is technically plausible; not a store listing or eligibility review |
| Orion | [Orion Store site](https://www.orionstore.net/about/), [upstream project](https://github.com/RookieEnough/Orion-Store) | Public repo/import/submission flow exists; acceptance requirements and account status not verified |
| IzzyOnDroid | [Inclusion policy](https://izzyondroid.org/docs/general/AppInclusionPolicy/), [FAQ](https://izzyondroid.org/faq/) | FOSS/dependency, APK signature, Fastlane metadata, policy and review apply; app includes third-party playback and rights are unresolved |
| Zapstore | [Zapstore docs](https://docs.zapstore.dev/) | Current packaging and publication acceptance not verified |
| Accrescent | [Developer docs](https://developer.accrescent.app/) | Current eligibility/review required; account-specific acceptance not verified |
| F-Droid | [Quick-start guide](https://f-droid.org/docs/Submitting_to_F-Droid_Quick_Start_Guide/), [documentation index](https://f-droid.org/en/docs/), [inclusion policy](https://f-droid.org/docs/Inclusion_Policy/) | Fastlane short description under 80 chars/no final dot, changelog max 500 chars; build-from-source/toolchain, dependency, attribution and IP review apply. F-Droid explicitly says it does not sign up for API keys and includes supplied keys in source and binary. |
| Self-hosted F-Droid | [F-Droid repository setup](https://f-droid.org/docs/Setup_an_F-Droid_App_Repo/) | Technical hosting is possible; signing/index operations and client trust are publisher responsibilities |
| APKPure | [Developer portal](https://developer.apkpure.com/) | Portal reachable as a product entry point; current public account, technical, content and policy criteria not independently verified |
| Uptodown | [Publisher criteria](https://www.uptodown.com/developers) | Current criteria include distribution/IP requirements; rights gate unresolved |
| Aptoide Connect | [Aptoide Connect](https://connect.aptoide.com/), [developer docs](https://docs.connect.aptoide.com/) | Account/review requirements may apply; current eligibility not verified |
| Amazon Appstore / Fire | [Amazon Appstore mobile changes](https://developer.amazon.com/apps-and-games/blogs/2025/02/upcoming-changes-to-amazon-appstore-for-android-devices-and-coins-program), [Fire app submission](https://developer.amazon.com/docs/app-submission/understanding-submission.html) | Amazon Android-device app submissions ended in 2025; Fire-device path remains distinct; IP documentation may be required; account eligibility not verified |
| Samsung Galaxy Store | [Seller Portal distribution](https://developer.samsung.com/galaxy-store/distribution), [prepare app](https://developer.samsung.com/galaxy-store/prepare-your-app) | Seller/account, metadata and policy requirements; exact HaseebFlix eligibility not verified |
| Huawei AppGallery | [AppGallery overview](https://developer.huawei.com/consumer/en/appgallery/), [AppGallery Connect overview](https://developer.huawei.com/consumer/en/doc/app/agc-help-overview-0000001100246618) | Publisher verification, metadata, privacy URL and release review; exact account/region/device eligibility not verified |

Official documentation and console-specific requirements are not uniform, and some portals require login. Where a channel's current limit, terms, account status, or acceptance could not be confirmed, this package labels it unverified rather than inferring eligibility.

## Local URLs

The app's configured privacy/settings URL is `https://haseebno1.github.io/Haseeb-Flix/`; live content and policy adequacy were not verified. The third-party player host is `https://embed.vidrift.net`; provider ownership, privacy and rights statements were not verified.
