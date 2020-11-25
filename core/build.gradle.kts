
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
    kotlin(BuildPlugins.kotlin_android_extention)
    kotlin(BuildPlugins.kotlin_kapt)
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

    buildFeatures.dataBinding = true

    buildTypes.forEach {
        try{
            it.buildConfigField(
                "String",
                "FIXER_API_KEY",
                getLocalProperty("fixer.access.key")
            )
        }catch (e: Exception){
            throw InvalidUserDataException("Define fixer.io API access key in local.properties " +
                    "file as 'fixer.access.key'. Visit https://fixer.io to get keys ")
        }
    }

}

dependencies {

    BuildDependencies.run {
        implementation(kotlin)
        implementation(kotlin_reflect)
        implementation(retrofit)
        implementation(retrofit_gson)
        implementation(http_logging)
        implementation(timber)
        implementation(room_db)
        implementation(room_ktx)
        implementation(livedata_ktx)
        implementation(data_store)
        implementation(recycler_view)
        implementation(preference)

        kapt(room_compiler)
    }

    // Tests
    addTestsDependencies()
}
