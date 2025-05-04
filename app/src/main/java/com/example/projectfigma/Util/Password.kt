package com.example.projectfigma.Util

import android.annotation.SuppressLint
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText

class Password {

    @SuppressLint("ClickableViewAccessibility")
    companion object{
        private var isPasswordVisible = false

        fun setIsVisable(passwordEditText: EditText){
            passwordEditText.setOnTouchListener { _, event ->
                val DRAWABLE_END = 2
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (passwordEditText.right - passwordEditText.compoundDrawables[DRAWABLE_END].bounds.width())) {
                        isPasswordVisible = !isPasswordVisible
                        if (isPasswordVisible) {
                            passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        } else {
                            passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        }
                        passwordEditText.setSelection(passwordEditText.text.length)
                        return@setOnTouchListener true
                    }
                }
                false
            }
        }
    }

}