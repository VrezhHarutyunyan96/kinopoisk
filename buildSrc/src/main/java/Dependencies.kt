import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    /*android core*/
    private val androidCore = arrayListOf(
        "androidx.appcompat:appcompat:${Versions.appcompat}",
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}",
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.refresh}",
        "androidx.activity:activity-ktx:${Versions.activity_ktx}",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_viewmodel}",
        "com.google.android.material:material:${Versions.material}",
        "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}",
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_ktx}",
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation_ktx}"
    )

    private val kotlinAndroid = arrayListOf(
        "androidx.core:core-ktx:${Versions.coreKtx}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}",
    )

    /*kotlin */
    private val kotlinNative = arrayListOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    )

    private val koinAndroid = arrayListOf(
        "org.koin:koin-androidx-scope:${Versions.koin}",
        "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    )

    /*koin*/
    private val koinNative = arrayListOf(
        "org.koin:koin-core:${Versions.koin}",
        "org.koin:koin-android:${Versions.koin}"
    )

    /*third party libraries*/
    private val thirdPartLibraries = arrayListOf(
        "io.coil-kt:coil:${Versions.coil}",
        "io.coil-kt:coil-svg:${Versions.coil}",
    )

    /*networking*/
    private val networking = arrayListOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.logging_interceptor}"
    )

    fun DependencyHandler.load(list: List<String>) = list.forEach { dependency ->
        add("implementation", dependency)
    }

    fun DependencyHandler.androidTestImplementation(list: List<String>) =
        list.forEach { dependency ->
            add("androidTestImplementation", dependency)
        }

    fun DependencyHandler.testImplementation(list: List<String>) = list.forEach { dependency ->
        add("testImplementation", dependency)
    }

    fun appLibraries() = kotlinAndroid + koinAndroid + androidCore + networking +
            kotlinNative + thirdPartLibraries

    fun dataLibraries() = kotlinNative + koinNative + networking

    fun domainLibraries() = kotlinNative + koinNative
}