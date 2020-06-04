object AndroidSdk {
    const val min = 21
    const val buildTools = "29.0.3"
    const val compile = 29
}

object AndroidLibraries {
    object Versions {
        const val core = "1.3.0"
        const val appCompat = "1.1.0"
        const val material = "1.1.0"
    }

    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object ComposeLibraries {
    object Versions {
        const val compose = "0.1.0-dev12"
        const val composeCompiler = "1.3.70-dev-withExperimentalGoogleExtensions-20200424"
    }

    const val layout = "androidx.ui:ui-layout:${Versions.compose}"
    const val material = "androidx.ui:ui-material:${Versions.compose}"
    const val liveData = "androidx.ui:ui-livedata:${Versions.compose}"
}

object KotlinLibraries {
    object Versions {
        const val kotlin = "1.3.72"
    }

    const val kotlinStdLibrary = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object ArrowLibraries {
    object Versions {
        const val arrow = "0.10.5"
    }

    const val core = "io.arrow-kt:arrow-core:${Versions.arrow}"
    const val syntax = "io.arrow-kt:arrow-syntax:${Versions.arrow}"
    const val kapt = "io.arrow-kt:arrow-meta:${Versions.arrow}"
}

object CoilLibrary {
    object Version {
        const val coil = "0.1.3"
    }

    const val coilCompose = "dev.chrisbanes.accompanist:accompanist-coil:${Version.coil}"
}

object NetworkLibraries {
    object Versions {
        const val gson = "2.8.6"
        const val retrofit = "2.8.1"
        const val okHttpInterceptor = "4.5.0"
    }

    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val adapterRx = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpInterceptor}"
}

object MultiThreadingLibraries {
    object Versions {
        const val rxJava = "2.2.19"
        const val rxAndroid = "2.1.1"
    }

    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
}

object TestLibraries {
    object Versions {
        const val jUnit = "4.13"
        const val androidJUnit = "1.1.1"
        const val androidEspresso = "3.2.0"
    }

    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val androidJUnit = "androidx.test.ext:junit:${Versions.androidJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidEspresso}"
}