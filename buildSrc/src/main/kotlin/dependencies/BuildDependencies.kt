package dependencies

/**
 * All dependencies used in this project.
 */
object BuildDependencies {

    const val recycler_view = "androidx.recyclerview:recyclerview:${BuildVersions.recycler_view}"
    const val data_store = "androidx.datastore:datastore-preferences:${BuildVersions.data_store}"
    const val preference = "androidx.preference:preference-ktx:${BuildVersions.preference}"
    const val material_spinner = "com.jaredrummler:material-spinner:${BuildVersions.material_spinner}"
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${BuildVersions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${BuildVersions.navigation}"
    const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${BuildVersions.lifecycle}"
    const val room_db = "androidx.room:room-runtime:${BuildVersions.room_db}"
    const val room_ktx = "androidx.room:room-ktx:${BuildVersions.room_db}"
    const val room_compiler = "androidx.room:room-compiler:${BuildVersions.room_db}"
    const val timber = "com.jakewharton.timber:timber:${BuildVersions.timber}"
    const val http_logging = "com.squareup.okhttp3:logging-interceptor:${BuildVersions.http_logging}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${BuildVersions.retrofit}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${BuildVersions.retrofit}"
    const val kotlin_coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${BuildVersions.kotlin_coroutines}"
    const val kotlin_coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${BuildVersions.kotlin_coroutines}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${BuildVersions.kotlin}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${BuildVersions.kotlin}"
    const val app_compat = "androidx.appcompat:appcompat:${BuildVersions.app_compat}"
    const val constraints_layout = "androidx.constraintlayout:constraintlayout:${BuildVersions.constraints_layout}"
    const val material_components = "com.google.android.material:material:${BuildVersions.material_components}"
}