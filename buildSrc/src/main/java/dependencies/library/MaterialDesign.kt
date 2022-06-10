package dependencies.library

import dependencies.base.implementation
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.materialDesign() {
    implementation("com.google.android.material:material:1.5.0")
}