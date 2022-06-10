package dependencies.library

import dependencies.base.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.retrofit() {
    implementation("com.squareup.retrofit2:retrofit:+")
    implementation("com.squareup.retrofit2:converter-gson:+")
}