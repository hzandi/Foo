object Dependencies {

    object Common {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
        const val inject = "javax.inject:javax.inject:1"
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    }

    object CommonTest {
        const val junit = "junit:junit:4.13.2"
        const val truth = "com.google.truth:truth:1.1.3"
        const val testCore = "androidx.test:core:1.4.1-alpha05"
        const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        const val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoVersion}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
    }

    object AppTest {
        const val espresso = "androidx.test.espresso:espresso-core:3.5.0-alpha05"
        const val testRunner = "androidx.test:runner:1.4.0"
        const val testRules = "androidx.test:rules:1.4.0"
        const val fragmentTesting = "androidx.fragment:fragment-testing:1.4.1"
        const val hiltAndroidTesting =
            "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    }

    object App {
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val preference = "androidx.preference:preference-ktx:1.2.0"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"
        const val security = "androidx.security:security-crypto:1.0.0"
        const val material = "com.google.android.material:material:1.5.0"
        const val firebase = "com.google.firebase:firebase-bom:29.3.0"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx:23.0.2"
        const val calligraphy = "io.github.inflationx:calligraphy3:3.1.1"
        const val viewPump = "io.github.inflationx:viewpump:2.0.3"
        const val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
        const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha03"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
        const val lifecycleCompiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1"
        const val materialProgressBar = "me.zhanghai.android.materialprogressbar:library:1.6.1"
        const val transitionsEverywhere = "com.andkulikov:transitionseverywhere:2.1.0"
        const val blurry = "jp.wasabeef:blurry:3.0.0"
        const val coil = "io.coil-kt:coil:2.0.0-rc03"
        const val coilGif = "io.coil-kt:coil-gif:2.0.0-rc03"
    }

    object Data {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val retrofitJsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
        const val retrofitScalarsConverter =
            "com.squareup.retrofit2:converter-scalars:${Versions.retrofitVersion}"
        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.3"
        const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
        const val room = "androidx.room:room-runtime:${Versions.roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
        // optional - Kotlin Extensions and Coroutines support for Room
        const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"
    }

    object ScrCast {
        const val dexter = "com.karumi:dexter:6.2.3"
        const val activity = "androidx.activity:activity-ktx:1.6.0-alpha01"
        const val fragment = "androidx.fragment:fragment-ktx:1.5.0-alpha04"
        const val localBroadcastManager =
            "androidx.localbroadcastmanager:localbroadcastmanager:1.1.0"
    }

}
