package com.tonycase.mytemplate.miscutilview

import android.content.DialogInterface
import android.graphics.drawable.Drawable

data class DialogSpec(
   val title: String,
   val message: String,
   val icon: Drawable,
   val buttonText: String = "Ok",
   val buttonAction: (dialog: DialogInterface?) -> Unit = { },
   val negativeButtonText: String? = null,   // default is no dismiss/cancel button
   val cancellable: Boolean = false
)
