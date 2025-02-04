package com.respirex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.respirex.data.Firebase
import kotlin.text.isBlank

class PasswordResetViewmodel : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> get() = _email

    private val _emailError = MutableLiveData("")
    val emailError: LiveData<String> get() = _emailError

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun validateEmail(email: String) {
        if (email.isBlank()) {
            _emailError.value = "Email cannot be empty"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailError.value = "Invalid Email"
        } else if (email.length > 30) {
            _emailError.value = "Email cannot be more than 30 characters"
        } else {
            _emailError.value = ""
        }
    }

    fun resetPassword(result: (Boolean,String) -> Unit){
        if(email.value.toString().isNotEmpty() && emailError.value.isNullOrEmpty()){
            Firebase.resetPassword(_email.value.toString()){ bool,result ->
                result(bool,result)
            }
        }
        else{
            result(false,"Fill details properly")
        }
    }

}