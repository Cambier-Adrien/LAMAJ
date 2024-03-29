plugins {
    id("com.android.application")
}

android {
    namespace = "com.android.lamaj"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.lamaj"
        minSdk = 19
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.preference:preference:1.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.google.code.gson:gson:2.8.9")
    implementation ("commons-codec:commons-codec:1.15")
    implementation ("com.airbnb.android:epoxy:4.6.0")
    implementation(files("libs\\pcap4j-core-1.8.2.jar"))
    implementation(files("libs\\pcap4j-packetfactory-propertiesbased-1.8.2.jar"))
    implementation(files("libs\\pcap4j-packetfactory-static-1.8.2.jar"))
    implementation(files("libs\\pcap4j-packettest-1.8.2-tests.jar"))
    implementation(files("libs\\pcap4j-sample-1.8.2.jar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}