
interface BuildType {

    companion object {
        const val DEBUG = "debug"
        const val RELEASE = "release"
    }

    val isMinifyEnabled: Boolean
    val isTestCoverageEnabled: Boolean
}

object BuildTypeDebug : BuildType {

    override val isMinifyEnabled: Boolean
        get() = false

    const val applicationIdSuffix = ".debug"
    const val versionNameSuffix = "-debug"
    override val isTestCoverageEnabled = true
}

object BuildTypeRelease : BuildType {

    override val isMinifyEnabled: Boolean
        get() = true
    override val isTestCoverageEnabled: Boolean
        get() = false
}