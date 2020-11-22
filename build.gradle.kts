import extentions.applyDefault

plugins.apply(BuildPlugins.git_hooks)

allprojects {
    repositories.applyDefault()

    plugins.apply(BuildPlugins.spotless)
    plugins.apply(BuildPlugins.ktlint)
}
