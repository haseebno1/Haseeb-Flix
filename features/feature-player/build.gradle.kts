plugins {
    id("haseebflix.android.library")
    id("haseebflix.android.library.compose")
    id("haseebflix.android.feature")
}

android.namespace = "com.haseebno1.haseebflix.feature.player"

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.webkit)

    testImplementation(libs.junit)
}
