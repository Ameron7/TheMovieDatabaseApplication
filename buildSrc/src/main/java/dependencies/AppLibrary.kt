package dependencies

import dependencies.library.gander
import dependencies.library.glide
import dependencies.library.youtubePlayer
import dependencies.library.*
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.appLibrary() {
    androidX()
    androidPaging()
    DaggerHilt()
    glide()
    gson()
    gander()
    materialDesign()
    NavGraph()
    okHttp()
    retrofit()
    vmLifeCycle()
    youtubePlayer()
    testUnit()
}