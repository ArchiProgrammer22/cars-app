package com.example.cars.presentation.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cars.DIALOG_BUTTON

class FullInfoDialogFragment(
    private val number: String,
    private val status: String,
    private val date: String,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(number)
            .setMessage("$status\n$date")
            .setPositiveButton(
                DIALOG_BUTTON
            ) { dialog, which -> dismiss() }
            .create()
    }
}