plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
}

android {
    compileSdk = Versions.compileSdkVersion
    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {

    implementation(Dependencies.Common.kotlinStdLib)
    implementation(Dependencies.Common.inject)
    implementation(Dependencies.Common.timber)
    implementation(Dependencies.Common.coroutinesCore)
    implementation(Dependencies.Common.coroutinesAndroid)
    implementation(Dependencies.Common.coreKtx)
    implementation(Dependencies.Data.retrofit)
    implementation(Dependencies.Data.retrofitJsonConverter)
    implementation(Dependencies.Data.retrofitScalarsConverter)
    implementation(Dependencies.Data.okhttpLoggingInterceptor)
    implementation(Dependencies.Data.gson)
    implementation(Dependencies.Data.room)
    kapt(Dependencies.Data.roomCompiler)
    implementation(Dependencies.Data.roomKtx)

    // Test
    testImplementation(Dependencies.CommonTest.junit)
    testImplementation(Dependencies.CommonTest.truth)
    testImplementation(Dependencies.CommonTest.testCore)
    testImplementation(Dependencies.CommonTest.coreTesting)
    testImplementation(Dependencies.CommonTest.mockito)
    testImplementation(Dependencies.CommonTest.mockitoInline)
    testImplementation(Dependencies.CommonTest.coroutinesTest)
}
