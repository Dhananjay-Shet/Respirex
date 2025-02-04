package com.respirex.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.respirex.data.Firebase
import com.respirex.data.ReportRepository

class LoginViewmodel : ViewModel() {

    // Email

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

    // Password

    private val _password = MutableLiveData("")
    val password: LiveData<String> get() = _password

    private val _passwordError = MutableLiveData("")
    val passwordError: LiveData<String> get() = _passwordError

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun validatePassword(password: String) {
        if (password.isBlank()) {
            _passwordError.value = "Password cannot be empty"
        } else if (password.length < 6) {
            _passwordError.value = "Password must be at least 6 characters"
        } else if (password.length > 20) {
            _passwordError.value = "Password cannot be more than 20 characters"
        } else {
            _passwordError.value = ""
        }
    }

    // Firebase

    fun verifyUser(result: (String) -> Unit) {
        if (emailError.value.isNullOrBlank() && passwordError.value.isNullOrBlank()) {
            Firebase.verifyUser(email.value.toString(), password.value.toString()) { result ->
                result(result)
            }
        }
        else{
            result("Fill details properly")
        }
    }

    fun getSignInIntent(): Intent {
        return Firebase.getSignInIntent()
    }

    fun fetchReport(){
        ReportRepository.fetchReport()
    }

}