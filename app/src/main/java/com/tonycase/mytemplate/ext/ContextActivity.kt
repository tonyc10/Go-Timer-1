package com.tonycase.mytemplate.ext

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.tonycase.mytemplate.miscutilview.DialogSpec

/**
 * @author Tony Case (case.tony@gmail.com)
 * Created on 11/11/20.
 */

fun Context.showDialog(dialogSpec: DialogSpec): AlertDialog {

    val dialogClickListener = object : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            dialogSpec.buttonAction(dialog)
        }
    }

    val builder = AlertDialog.Builder(this)
        .setTitle(dialogSpec.title)
        .setMessage(dialogSpec.message)
        .setIcon(dialogSpec.icon)
        .setPositiveButton(dialogSpec.buttonText, dialogClickListener)
        .setCancelable(dialogSpec.cancellable)

    dialogSpec.negativeButtonText?.let {
        builder.setNegativeButton(it) { dialog, _ -> dialog.dismiss() }
    }

    return builder.show()
}
