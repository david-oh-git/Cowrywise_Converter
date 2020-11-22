plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
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
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
    const val update_gradle_plugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.update_gradle_plugin}"

}

dependencies {
    implementation(Plugins.build_tools)
    implementation(Plugins.kotlin)
    implementation(Plugins.spotless)
    implementation(Plugins.ktlint)
    implementation(Plugins.update_gradle_plugin)
}