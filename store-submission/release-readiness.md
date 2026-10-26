# Release readiness

**Overall: blocked for public distribution.** Listing completeness and publication eligibility are separate; neither is complete.

| Check | Finding | Evidence/status |
|---|---|---|
| Branch/source | `chore/haseebflix-branding-and-releases`, `cf737a3182aff6846c296eb66134d4d8c66877f1` | Repo-confirmed at audit |
| Application ID | `com.haseebno1.haseebflix` | Repo-confirmed |
| Version | 1.1.0 / code 8 | Repo-confirmed |
| Build configuration | min API 23; compile/target SDK 36 | Repo-confirmed |
| PR CI | debug build, release-variant build, `testDebugUnitTest`, artifact uploads all passed | [Run 40251960296](https://github.com/haseebno1/Haseeb-Flix/actions/runs/40251960296), commit SHA above |
| Static checks | CI does not invoke lint, Detekt, or Spotless | Workflow inspection; not run in this audit |
| CI optimized artifact | Debug-signing fallback, placeholder TMDB key | Explicitly not distributable; package/signer/artifact contents not independently inspected here |
| Tagged release workflow | Requires signing secrets and `TMDB_API_KEY`; has not run | Repo/workflow-confirmed |
| Existing public release | Legacy asset metadata exists; binary provenance, signer, package, version and behavior not verified | Not verified |
| Release tag | None found at audit | Repo-confirmed |
| TMDB key path | `local.properties` `haseebflix.apikey` or env `HASEEBFLIX_API_KEY` → `BuildConfig.HASEEBFLIX_API_KEY` → network module/interceptor; release workflow maps secret `TMDB_API_KEY` to that env | Source/workflow-confirmed; runtime validity not verified |
| TMDB terms | Free use subject to attribution for qualifying noncommercial use; commercial use requires contact/licensing discussion | Official FAQ; publisher must establish its own status and comply |
| Signing keys/API credentials | None created or exposed in docs | Publisher action required; no secret values recorded |
| TMDB app attribution | Required notice added to About/content-sources in Settings; approved official logo still missing | User UI work partly complete; logo asset blocked by outbound DNS in this environment |
| Android backup | Explicit API 23–30 and API 31+ rules include only the Room database for cloud backup/device transfer | Source-confirmed; verify merged manifest and restore behavior in CI/device testing |
| Rights, privacy, publisher account | Unresolved | Publication blocker |

The source-to-build TMDB configuration is internally consistent; no evidence supports changing app code or adding a backend. The API key is compiled into the client and can be extracted from an APK, so it is not a confidential server credential. A valid key, verified release artifact and clean-install TMDB test are unavailable in this environment. No local Android build was run because JDK/Android SDK prerequisites were unavailable. Existing CI evidence is not a substitute for verifying a new documentation commit or final signed artifact.

## Dependency-ordered next steps

1. Resolve content/provider and artwork rights; decide permitted distribution territories.
2. Confirm publisher identity/account eligibility, support and policy URL; audit provider and backup behavior.
3. Finalize truthful privacy declarations, ratings and listing placeholders; obtain store-specific current specs.
4. Install signing secrets and a terms-compliant TMDB key in the repository settings; protect values and review key exposure/rate-limit risks.
5. Run CI/static checks and produce a signed candidate; inspect package/version/signer/source SHA and TMDB configuration without publishing.
6. Test clean install, catalogue/search/images, watchlist, playback, navigation/network failures and upgrade path on supported devices.
7. Reassess the chosen channel's current requirements; publisher may then authorize a submission. No tag, release, submission, or publication was performed here.
