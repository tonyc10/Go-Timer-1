package com.tonycase.mytemplate.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tonycase.mytemplate.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint         // not really needed since we don't inject anything.  Just as example
class MainActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      Timber.i("TC onCreate()")
      Log.i("MainActivity", "onCreate()")
      setContentView(R.layout.main_activity)
      if (savedInstanceState == null) {
         supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
      }
   }
}