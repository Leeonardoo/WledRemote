package br.com.leonardo.wledremote.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import br.com.leonardo.wledremote.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object WledDialogUtil {

    fun infoDialog(
        context: Context,
        title: String,
        message: String,
        positiveButton: String
    ): AlertDialog {
        return MaterialAlertDialogBuilder(context, R.style.RoundedAlertDialog).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButton) { dialog, _ -> dialog.dismiss() }
        }.show()
    }
}