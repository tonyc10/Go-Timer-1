package com.tonycase.mytemplate.di

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.WindowManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/10/20.
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun inflater(activity: Activity): LayoutInflater {
        return activity.layoutInflater
    }

    @Provides
    fun windowManager(activity: Activity): WindowManager {
        return activity.windowManager
    }

    @Provides
    fun theme(activity: Activity): Resources.Theme {
        return activity.theme
    }
}
