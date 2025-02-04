package com.respirex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.respirex.data.Patient
import com.respirex.data.ReportRepository

class FormViewmodel : ViewModel() {

    // Firstname

    private val _firstname = MutableLiveData("")
    val firstname: LiveData<String> get() = _firstname

    private val _firstnameError = MutableLiveData("")
    val firstnameError: LiveData<String> get() = _firstnameError

    fun updateFirstname(firstname: String) {
        _firstname.value = firstname
    }

    fun validateFirstname(name: String) {
        if (name.isBlank()) {
            _firstnameError.value = "Firstname cannot be empty"
        } else if (name.length < 2) {
            _firstnameError.value = "Firstname cannot be less than 2 characters"
        } else if (name.length > 20) {
            _firstnameError.value = "Firstname cannot be more than 20 characters"
        } else {
            _firstnameError.value = ""
        }
    }

    // Lastname

    private val _lastname = MutableLiveData("")
    val lastname: LiveData<String> get() = _lastname

    private val _lastnameError = MutableLiveData("")
    val lastnameError: LiveData<String> get() = _lastnameError

    fun updateLastname(lastname: String) {
        _lastname.value = lastname
    }

    fun validateLastname(name: String) {
        if (name.isBlank()) {
            _lastnameError.value = "Lastname cannot be empty"
        } else if (name.length < 3) {
            _lastnameError.value = "Lastname cannot be less than 3 characters"
        } else if (name.length > 20) {
            _lastnameError.value = "Lastname cannot be more than 20 characters"
        } else {
            _lastnameError.value = ""
        }
    }

    // Gender

    private val _isMale = MutableLiveData(true)
    val isMale: LiveData<Boolean> get() = _isMale

    private val _selectedGender = MutableLiveData("Male")
    val selectedGender: LiveData<String> get() = _selectedGender

    fun updateToMale() {
        _isMale.value = true
        _selectedGender.value = "Male"
    }

    fun updateToFemale() {
        _isMale.value = false
        _selectedGender.value = "Female"
    }

    // DOB

    private val _dob = MutableLiveData("")
    val dob: LiveData<String> get() = _dob

    private val _age = MutableLiveData(0)
    val age: LiveData<Int> get() = _age

    fun updateDOB(dob: String) {
        _dob.value = dob
    }

    fun updateAge(age: Int) {
        _age.value = age
    }

    // Blood Group

    private val _bloodGrp = MutableLiveData("A+")
    val bloodGrp: LiveData<String> get() = _bloodGrp

    fun updateBloodGrp(bloodGrp: String) {
        _bloodGrp.value = bloodGrp
    }

    // Mobile Number

    private val _mobileNumber = MutableLiveData("")
    val mobileNumber: LiveData<String> get() = _mobileNumber

    private val _mobileNumberError = MutableLiveData("")
    val mobileNumberError: LiveData<String> get() = _mobileNumberError

    fun updateMobileNumber(mobileNumber: String) {
        _mobileNumber.value = mobileNumber
    }

    fun validateMobileNumber(number: String) {
        if (number.isBlank()) {
            _mobileNumberError.value = "Mobile number cannot be empty"
        } else if (number.length < 10) {
            _mobileNumberError.value = "Mobile number cannot be less than 10 characters"
        } else if (number.length > 10) {
            _mobileNumberError.value = "Mobile number cannot be more than 10 characters"
        } else {
            _mobileNumberError.value = ""
        }
    }

    // Symptoms

    private val _symptoms = MutableLiveData("")
    val symptoms: LiveData<String> get() = _symptoms

    private val _symptomsError = MutableLiveData("")
    val symptomsError: LiveData<String> get() = _symptomsError

    fun updateSymptoms(symptoms: String) {
        _symptoms.value = symptoms
    }

    fun validateSymptoms(symptoms: String) {
        if (symptoms.isBlank()) {
            _symptomsError.value = "Symptoms cannot be empty"
        } else if (symptoms.length > 50) {
            _symptomsError.value = "Symptoms cannot be more than 50 characters"
        } else {
            _symptomsError.value = ""
        }
    }

    // Diagnostic disease

    private val _disease = MutableLiveData("Covid-19")
    val disease: LiveData<String> get() = _disease

    fun updateDisease(disease: String) {
        _disease.value = disease
    }

    // Submit Form

    fun isValid(): Boolean {
        return !firstname.value.isNullOrBlank() &&
               !lastname.value.isNullOrBlank() &&
               !mobileNumber.value.isNullOrBlank() &&
               !symptoms.value.isNullOrBlank() &&
                firstnameError.value.isNullOrBlank() &&
                lastnameError.value.isNullOrBlank() &&
                mobileNumberError.value.isNullOrBlank() &&
                symptomsError.value.isNullOrBlank() &&
                !dob.value.isNullOrBlank()
    }

    fun patientObj(){
        val patient=Patient(
            firstname.value.toString(),
            lastname.value.toString(),
            selectedGender.value.toString(),
            dob.value.toString(),
            age.value.toString().toInt(),
            bloodGrp.value.toString(),
            mobileNumber.value.toString(),
            symptoms.value.toString(),
            disease.value.toString()
        )
        ReportRepository.setPatient(patient)
    }

}