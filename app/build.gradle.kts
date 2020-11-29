import BuildAndroidConfig.application_id
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

plugins {
    id(BuildPlugins.android_application)
    kotlin(BuildPlugins.kotlin_android)
    kotlin(BuildPlugins.kotlin_android_extention)
    kotlin(BuildPlugins.kotlin_kapt)
    id(BuildPlugins.navigation_safe_args)
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

    signingConfigs {
        create(BuildType.RELEASE) {
            keyAlias = getLocalProperty("signing.key.alias")
            keyPassword = getLocalProperty("signing.key.password")
            storeFile = file(getLocalProperty("signing.store.file"))
            storePassword = getLocalProperty("signing.store.password")

            isV2SigningEnabled = true
        }
    }

    buildTypes {

        getByName(BuildType.RELEASE){
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
            proguardFiles( getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs.getByName(name)
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

    buildFeatures{
        dataBinding = true
    }

}

dependencies {
    implementation( fileTree( mapOf( "dir" to "libs", "include" to  listOf("*.jar")  )))

    implementation( project(BuildModules.CORE))

    BuildDependencies.run {
        implementation(kotlin)
        implementation(app_compat)
        implementation(material_components)
        implementation(constraints_layout)
        implementation(kotlin_coroutines_android)
        implementation(kotlin_coroutines_core)
        implementation(timber)
        implementation(navigation_fragment)
        implementation(navigation_ui)
        implementation(livedata_ktx)
    }

    addTestsDependencies()
}
