package com.example.recipemaker.utils

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.recipemaker.R
import com.example.recipemaker.utils.Constants.VALUE_REQUIRED
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Activity.snackBar(text: String, view: View){
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        .show()
}

fun Activity.isInputEmpty(editText: TextInputEditText, errorMessage: Boolean = false) : Boolean {
    return if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })){
        if (errorMessage){
            editText.error = VALUE_REQUIRED
        }
        true
    } else {
        false
    }
}