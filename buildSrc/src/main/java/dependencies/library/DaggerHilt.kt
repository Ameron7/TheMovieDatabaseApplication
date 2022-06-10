package dependencies.library

import dependencies.base.implementation
import dependencies.base.kapt
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.DaggerHilt(){
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")
}