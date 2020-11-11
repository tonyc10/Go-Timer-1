package com.tonycase.mytemplate.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import com.tonycase.mytemplate.data.DateProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/10/20.
 */

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    fun applicationInfo(application: Application): ApplicationInfo {
        return application.applicationInfo
    }

    @Provides
    fun packageManager(application: Application): PackageManager {
        return application.packageManager
    }

    @Provides
    fun context(application: Application): Context {
        return application.baseContext
    }

    @Provides
    fun resources(application: Application): Resources {
        return application.resources
    }

    @Provides
    fun dateProvider(): DateProvider {
        return DateProvider()
    }

    /** Has Singleton annotation because now we have mutable status.  All of the above have non-mutable status. */
    @Provides
    @Singleton
    fun sharedPreferences(application: Application): SharedPreferences {
        // TODO: change this from "Template" to app name
        return application.getSharedPreferences("Template", Context.MODE_PRIVATE)
    }
}