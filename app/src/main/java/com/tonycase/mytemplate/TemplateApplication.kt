package com.tonycase.mytemplate

import android.app.Application
import android.util.Log
import com.tonycase.mytemplate.ext.isDebugBuild
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/10/20.
 */
@HiltAndroidApp
class TemplateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
    }

    private fun initializeTimber() {

        if (isDebugBuild()) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
//            when (priority) {
//                Log.INFO -> FirebaseCrashlytics.getInstance().log( (tag ?: "") + ", $message")
//                Log.WARN -> FirebaseCrashlytics.getInstance().log("Warning: " + (tag ?: "") + ", $message")
//                Log.ERROR -> FirebaseCrashlytics.getInstance().log("ERROR: " + (tag ?: "") + ", $message")
//                else -> {}   // skip other log levels
//            }
//            if (t != null) {
//                FirebaseCrashlytics.getInstance().recordException(t)
//            }
            super.log(priority, tag, message, t)
        }
    }
}
