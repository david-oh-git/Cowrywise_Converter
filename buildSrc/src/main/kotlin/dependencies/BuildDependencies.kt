package dependencies

/**
 * All dependencies used in this project.
 */
object BuildDependencies {

    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildVersions.kotlin_coroutines}"
    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildVersions.kotlin_coroutines}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${BuildVersions.kotlin}"
    const val app_compat = "androidx.appcompat:appcompat:${BuildVersions.app_compat}"
    const val constraints_layout = "androidx.constraintlayout:constraintlayout:${BuildVersions.constraints_layout}"
    const val material_components = "com.google.android.material:material:${BuildVersions.material_components}"
}