package com.respirex.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.respirex.data.Firebase
import com.respirex.data.ReportRepository

class RegistrationViewmodel : ViewModel() {

    // Image

    private val _painterResource =
        MutableLiveData<Uri>(Uri.parse("android.resource://com.respirex/drawable/profile"))
    val painterResource: LiveData<Uri> = _painterResource

    // Name

    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    private val _nameError = MutableLiveData("")
    val nameError: LiveData<String> get() = _nameError

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun validateName(name: String) {
        if (name.isBlank()) {
            _nameError.value = "Name is required"
        } else if (name.length < 2) {
            _nameError.value = "Name must be at least 2 characters"
        } else if (name.length > 20) {
            _nameError.value = "Name cannot be more than 20 characters"
        } else {
            _nameError.value = ""
        }
    }

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
        } else if (!password.matches(".*[A-Z].*".toRegex())) {
            _passwordError.value = "Password must contain at least one uppercase letter"
        } else if (!password.matches(".*[a-z].*".toRegex())) {
            _passwordError.value = "Password must contain at least one lowercase letter"
        } else if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {
            _passwordError.value = "Password must contain at least one special character"
        } else if (!password.matches(".*[0-9].*".toRegex())) {
            _passwordError.value = "Password must contain at least one number"
        } else if (password.matches(".*\\s.*".toRegex())) {
            _passwordError.value = "Password cannot contain spaces"
        } else if (password.length < 6) {
            _passwordError.value = "Password must be at least 6 characters"
        } else if (password.length > 20) {
            _passwordError.value = "Password cannot be more than 20 characters"
        } else {
            _passwordError.value = ""
        }
    }

    // Firebase

    fun registerUser(result: (String) -> Unit) {
        if (nameError.value.isNullOrBlank() && emailError.value.isNullOrEmpty() && passwordError.value.isNullOrBlank()) {
            Firebase.registerUser(
                _painterResource.value!!,
                _name.value.toString(),
                _email.value.toString(),
                _password.value.toString()
            ) { result ->
                result(result)
            }
        }
        else{
            result("Fill details properly")
        }
    }

    fun fetchReport() {
        ReportRepository.fetchReport()
    }

}