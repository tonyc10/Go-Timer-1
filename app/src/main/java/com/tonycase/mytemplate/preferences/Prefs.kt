package com.tonycase.mytemplate.preferences

import android.content.SharedPreferences
import javax.inject.Inject

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/10/20.
 */

class Prefs @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private val KEY_NEW_TO_APP = "key_new_to_app"

    fun isNewToApp() = getBoolean(KEY_NEW_TO_APP, true)
    fun setNotNewToApp() = putBoolean(KEY_NEW_TO_APP, false)


    /////   Helper methods per data type

    private fun getString(key: String): String? = sharedPreferences.getString(key, null)

    private fun putString(key: String, value: String): Boolean {
        return sharedPreferences
            .edit()
            .putString(key, value)
            .commit()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    private fun putBoolean(key: String, value: Boolean): Boolean {
        return sharedPreferences
            .edit()
            .putBoolean(key, value)
            .commit()
    }

    private fun getInt(key: String, defaultValue: Int): Int =
        sharedPreferences.getInt(key, defaultValue)

    private fun putInt(key: String, value: Int): Boolean {
        return sharedPreferences
            .edit()
            .putInt(key, value)
            .commit()
    }

    private fun getLong(key: String, defaultValue: Long): Long =
        sharedPreferences.getLong(key, defaultValue)

    private fun putLong(key: String, value: Long): Boolean {
        return sharedPreferences
            .edit()
            .putLong(key, value)
            .commit()
    }

    private fun getFloat(key: String, defaultValue: Float = Float.NaN): Float =
        sharedPreferences.getFloat(key, defaultValue)

    private fun putFloat(key: String, value: Float): Boolean {
        return sharedPreferences
            .edit()
            .putFloat(key, value)
            .commit()
    }
}
