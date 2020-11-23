
import BuildAndroidConfig.build_tools_version
import BuildAndroidConfig.compile_sdk_version
import BuildAndroidConfig.minimum_sdk_version
import BuildAndroidConfig.target_sdk_version
import BuildAndroidConfig.test_instrumentation_runner
import BuildAndroidConfig.version_code
import BuildAndroidConfig.version_name
import dependencies.BuildDependencies
import extentions.addTestsDependencies
import extentions.getLocalProperty

plugins{
    id(BuildPlugins.android_library)
    kotlin(BuildPlugins.kotlin_android)
    kotlin(BuildPlugins.kotlin_kapt)
    kotlin(BuildPlugins.kotlin_android_extention)
}

android {
    compileSdkVersion(compile_sdk_version)
    buildToolsVersion(build_tools_version)

    defaultConfig {
        minSdkVersion(minimum_sdk_version)
        targetSdkVersion(target_sdk_version)
        versionCode = version_code
        versionName = version_name

        testInstrumentationRunner = test_instrumentation_runner
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

}

dependencies {
    implementation( fileTree( mapOf( "dir" to "libs", "include" to  listOf("*.jar")  )))

    implementation(BuildDependencies.kotlin)
    implementation(BuildDependencies.retrofit)
    implementation(BuildDependencies.retrofit_gson)
    implementation(BuildDependencies.http_logging)
    implementation(BuildDependencies.timber)
    implementation(BuildDependencies.room_db)
    implementation(BuildDependencies.room_ktx)

    kapt(BuildDependencies.room_compiler)

    addTestsDependencies()
}
