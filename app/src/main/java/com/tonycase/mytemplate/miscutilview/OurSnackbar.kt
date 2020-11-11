package com.tonycase.mytemplate.miscutilview

import android.content.Context
import android.graphics.Color
import android.os.Vibrator
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.tonycase.mytemplate.R

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 5/21/19.
 */
enum class SnackbarColor(val bgColorRes: Int, val fgColorDark: Boolean) {

    GREEN_GO(R.color.green, false),
    RED_BAD(R.color.warning_red, false),
    PRIMARY(R.color.primary, false),
    NEUTRAL(R.color.md_grey_700, false),
    ACCENT(R.color.accent, true)
}

data class SnackbarSpec(
   val text: String,
   val color: SnackbarColor,
   val bigFont: Boolean = false,
   val duration: Int = Snackbar.LENGTH_LONG,        // one of Snackbar length indefinite
   val vibrate: Boolean = false
) {

   fun bgColor(context: Context) = ContextCompat.getColor(context, color.bgColorRes)
}

fun playSnackbar(
   rootView: View,
   baseContext: Context,
   spec: SnackbarSpec,
   afterSnackbar: () -> Unit = { }
): Snackbar {

   // Android Snackbar with our colors, text size, text and duration
   val duration = spec.duration

   val snackbar = Snackbar.make(rootView, spec.text, duration)
   val snackbarView = snackbar.view
   val textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
   val fgColor = if (spec.color.fgColorDark) Color.BLACK else Color.WHITE
   textView.setTextColor(fgColor)
   if (spec.bigFont) {
      textView.textSize = 21f
   }
   snackbarView.setBackgroundColor(spec.bgColor(baseContext))
   snackbar.show()

   // vibration if any
   if (spec.vibrate) {
      val v = baseContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
      @Suppress("DEPRECATION")   // replacement added in API 26
      v.vibrate(750)
   }

   snackbar.view.isClickable = true
   snackbar.view.setOnClickListener { snackbar.dismiss() }

   // callback after Snackbar
   snackbar.addCallback(object : Snackbar.Callback() {
      override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
         super.onDismissed(transientBottomBar, event)
         afterSnackbar.invoke()
      }
   })
   return snackbar
}
