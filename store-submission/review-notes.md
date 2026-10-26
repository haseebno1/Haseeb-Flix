# Internal reviewer notes — do not publish as listing copy

## Publication blockers

- **Content rights/provider:** third-party playback provider is embedded; rights to the provider's streams and any referenced title artwork are not established. Get written authorization/legal review before distribution. Do not imply approval.
- **Provider outreach draft (not sent):** “Can you confirm the content on your platform is authorized/licensed for redistribution through our app acting as a third-party aggregator? If authorization depends on attribution or license terms, what exactly is required?” Keep publication on hold until a written answer is clear and retained.
- **Publisher identity:** confirm legal publisher, support contact, website, privacy policy, target territories and store account eligibility.
- **Privacy:** inspect provider runtime behavior and Android backup implications; complete truthful jurisdiction/store declarations.
- **Release artifact:** CI's optimized variant is debug-signed and uses placeholder TMDB key. It is not a production release. No release tag has been created.
- **TMDB:** source-to-build injection path exists, but live key behavior in a release artifact was not verified. Confirm applicable TMDB commercial/noncommercial terms, attribution and brand rules.
- **TMDB attribution gap:** the required notice is now shown in the About/content-sources area in Settings. The approved logo is still missing and must be added unaltered, smaller than HaseebFlix branding, before release.
- **Assets:** artwork and trademark/streaming screenshots need documented rights review.

## Claims supported by repository inspection

Package `com.haseebno1.haseebflix`; version 1.1.0/code 8; Android min API 23; catalog browsing/search/details/watchlist; third-party WebView playback; Room persistence; internet and network-state permissions. Treat all as repo-confirmed, not as proof of operational correctness or policy compliance.

## Placeholder register — resolve before console entry

`[PRIMARY_CATEGORY]`, `[DEVELOPER_EMAIL]`, `[MARKETING_URL]`, `[PRIVACY_POLICY_URL]`, `[DISTRIBUTION_COUNTRIES]`, `[PRICE]`, `[AGE_RATING]`, `[CONTENT_RIGHTS_EVIDENCE]`.

Review note: the untracked parent guide is maintained at the user's requested location; this in-repository package is canonical for this branch pending publisher review.
