@file:Suppress("SpellCheckingInspection")

import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    maven("https://ci.android.com/builds/submitted/6043188/androidx_snapshot/latest/repository/")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

object Plugins {

    private object Versions {
        const val build_tools = "4.1.1"
        const val kotlin = "1.4.10"
        const val spotless = "5.6.1"
        const val ktlint = "0.39.0"
        const val update_gradle_plugin = "0.36.0"
    }

    const val build_tools = "com.android.tools.build:gradle:${Versions.build_tools}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
    const val update_gradle_plugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.update_gradle_plugin}"

}

dependencies {
    implementation(Plugins.build_tools)
    implementation(Plugins.kotlin_gradle_plugin)
    implementation(Plugins.spotless)
    implementation(Plugins.ktlint)
    implementation(Plugins.update_gradle_plugin)
}
