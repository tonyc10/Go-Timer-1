package com.tonycase.mytemplate.ext

import android.os.Build
import com.tonycase.mytemplate.BuildConfig

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/10/20.
 */

/** Used in log statements to identify specific instances, not just class.*/
fun Any.hashString() = this.hashCode().rem(0x1000000).toString(16)

fun isDebugBuild() = BuildConfig.DEBUG
fun atLeastOreo() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
fun atLeastPie() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
fun atLeastQ() = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
