package dependencies

object TestDependencies {

    const val mock_web_server = "com.squareup.okhttp3:mockwebserver:${BuildVersions.mock_web_server}"
    const val androidx_test_core = "androidx.test:core:${BuildVersions.androidx_test_core}"
    const val robolectric = "org.robolectric:robolectric:${BuildVersions.robolectric}"
    const val androidx_junit_runner = "androidx.test:runner:${BuildVersions.androidx_runner}"
    const val androidx_junit_rules = "androidx.test:rules:${BuildVersions.androidx_runner}"
    const val junit = "junit:junit:${BuildVersions.junit}"
    const val junit_vantage = "org.junit.vintage:junit-vintage-engine:${BuildVersions.junit5}"
    const val junit5_api = "org.junit.jupiter:junit-jupiter-api:${BuildVersions.junit5}"
    const val junit5_engine = "org.junit.jupiter:junit-jupiter-engine:${BuildVersions.junit5}"
    const val junit5_platform = "org.junit.platform:junit-platform-launcher:${BuildVersions.junit_platform}"
    const val androidx_junit = "androidx.test.ext:junit:${BuildVersions.androidx_junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${BuildVersions.espresso}"
    const val truth = "com.google.truth:truth:${BuildVersions.truth}"
    const val mockito = "org.mockito:mockito-core:${BuildVersions.mockito}"
    const val hamcrest = "org.hamcrest:hamcrest:${BuildVersions.hamcrest}"
    const val arch_core = "androidx.arch.core:core-testing:${BuildVersions.arch_core}"
    const val coroutine_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${BuildVersions.coroutine_test}"
    const val mockito_kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${BuildVersions.mockito_kotlin}"
    const val mockk = "io.mockk:mockk:${BuildVersions.mockk}"
}