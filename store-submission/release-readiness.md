# Release readiness

**Overall: blocked for public distribution.** Listing completeness and publication eligibility are separate; neither is complete.

| Check | Finding | Evidence/status |
|---|---|---|
| Branch/source | `chore/haseebflix-branding-and-releases`; authorization record committed locally as `4f015a2` | Repo-confirmed locally; update still needs CI |
| Application ID | `com.haseebno1.haseebflix` | Repo-confirmed |
| Version | 1.1.0 / code 8 | Repo-confirmed |
| Build configuration | min API 23; compile/target SDK 36 | Repo-confirmed |
| PR CI | debug build, release-variant build, unit tests, artifact uploads passed on the previously pushed submission package | [Run 40261025705](https://github.com/haseebno1/Haseeb-Flix/actions/runs/40261025705); latest local authorization/privacy documentation still needs a new CI run |
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
| VidRift embed permission | Provider email supplied by publisher says embedding is allowed and free, with framing/ad conditions | Documented in `docs/content-authorization.md`; sender identity not independently authenticated |
| Underlying media rights | VidRift says it does not own or store media; no title-level licenses supplied | Not established by the embed permission email; publisher should review before any public distribution claim |
| Privacy, publisher account | Publisher identity/contact not confirmed; provider storage/ad behavior not independently audited; policy remains a draft | Publication material incomplete |

The source-to-build TMDB configuration is internally consistent; no evidence supports changing app code or adding a backend. The API key is compiled into the client and can be extracted from an APK, so it is not a confidential server credential. A valid key, verified release artifact and clean-install TMDB test are unavailable in this environment. No local Android build was run because JDK/Android SDK prerequisites were unavailable. Existing CI evidence is not a substitute for verifying a new documentation commit or final signed artifact.

## Dependency-ordered next steps

1. Add the official TMDB logo asset and retain the exact required notice in About/Credits.
2. Confirm publisher display/legal identity and a monitored privacy contact; review provider storage/ad behavior and finalize the policy at the stable app URL.
3. Add signing and TMDB secrets directly in repository Actions settings; secret values are not available to this workspace.
4. Push the branch updates and review CI; verify static checks if added to workflow.
5. Only with secrets configured, merge when approved to trigger the main-branch build; inspect APK package/version/signer/source SHA and test TMDB on a clean device.
6. Create the matching release tag only after those checks pass. Do not submit the app until the publisher has reviewed the content-rights scope and all selected store requirements.
