buildscript {

    dependencies {

        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    }
}

repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}
