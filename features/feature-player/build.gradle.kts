plugins {
    id("cinemax.android.library")
    id("cinemax.android.library.compose")
    id("cinemax.android.feature")
}

android.namespace = "com.maximillianleonov.cinemax.feature.player"

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.webkit)

    testImplementation(libs.junit)
}
