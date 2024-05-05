
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.dokka")
}

android {
    namespace = "com.example.proyectohospitalgambia"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.proyectohospitalgambia"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Configuración de Dokka
    // Configuración de Dokka
    tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
        moduleName.set("MyGnuHealthApp")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }




}

dependencies {


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    // Se puede usar para los editText
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.media3:media3-common:1.3.1")


    // NavComponent
    val navVersion = "2.7.0"
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.4.0")
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    // Retrofit
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.google.code.gson:gson:2.8.8")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // SharedPreferences
    implementation("androidx.preference:preference-ktx:1.2.1")

    //ByCryp
    implementation("org.mindrot:jbcrypt:0.4")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("io.mockk:mockk:1.12.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Dokka
    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.20")


}

