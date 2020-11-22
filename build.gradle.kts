import extentions.applyDefault


allprojects {
    repositories.applyDefault()

    plugins.apply(BuildPlugins.spotless)
    plugins.apply(BuildPlugins.ktlint)
}
