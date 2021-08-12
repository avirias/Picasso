package com.avirias.picasso.ui.common

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showErrorDialog(
    message: String = "Something went wrong",
    listener: (DialogInterface) -> Unit = {}
): AlertDialog {
    val alertDialog: AlertDialog = AlertDialog.Builder(requireContext())
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            listener(dialog)
        }
        .create()

    try {
        if (isAdded)
        alertDialog.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return alertDialog
}