package dependencies.library

import dependencies.base.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.gson() {
    implementation("com.google.code.gson:gson:+")
}