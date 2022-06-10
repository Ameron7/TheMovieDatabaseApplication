package id.indocyber.themoviedatabaseapplication

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TheMovieDatabaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            Log.e("testTag",e.message.orEmpty(), e)
        }
    }
}