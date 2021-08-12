package com.avirias.picasso.common

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showErrorDialog(
    message: String = "Something went wrong",
    listener: (DialogInterface) -> Unit = {}
) {
    val alertDialog: AlertDialog = AlertDialog.Builder(requireContext())
        .setTitle("Error")
        .setMessage(message)
        .setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            listener(dialog)
        }
        .create()

    try {
        alertDialog.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}