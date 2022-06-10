package dependencies.library

import dependencies.base.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidPaging() {
    implementation("androidx.paging:paging-runtime:3.1.1")
}