import BuildAndroidConfig.application_id
import BuildAndroidConfig.build_tools_version
import BuildAndroidConfig.compile_sdk_version
import BuildAndroidConfig.minimum_sdk_version
import BuildAndroidConfig.target_sdk_version
import BuildAndroidConfig.test_instrumentation_runner
import BuildAndroidConfig.version_code
import BuildAndroidConfig.version_name
import dependencies.BuildDependencies.app_compat
import dependencies.BuildDependencies.constraints_layout
import dependencies.BuildDependencies.kotlin_coroutines_android
import dependencies.BuildDependencies.kotlin_coroutines_core
import dependencies.BuildDependencies.material_components
import dependencies.BuildDependencies

plugins {
    id(BuildPlugins.android_application)
    kotlin (BuildPlugins.kotlin_android)
    kotlin(BuildPlugins.kotlin_kapt)
    kotlin(BuildPlugins.kotlin_android_extention)
}

android {
    compileSdkVersion(compile_sdk_version)
    buildToolsVersion(build_tools_version)

    defaultConfig {
        applicationId = application_id
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

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }

    buildTypes {

        getByName(BuildType.RELEASE){
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles( getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG){
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }

    }

    lintOptions {
        lintConfig = rootProject.file(".lint/config.xml")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
    }
}

dependencies {
    implementation( fileTree( mapOf( "dir" to "libs", "include" to  listOf("*.jar")  )))

    implementation(BuildDependencies.kotlin)
    implementation(app_compat)
    implementation(material_components)
    implementation(constraints_layout)
    implementation(kotlin_coroutines_android)
    implementation(kotlin_coroutines_core)
}
