package com.respirex.data

data class Patient(
    val firstname: String = "",
    val lastname: String = "",
    val gender: String = "",
    val dob: String = "",
    val age: Int = -1,
    val bloodGrp: String = "",
    val mobileNumber: String =" ",
    val symptoms: String = "",
    val disease: String = ""
)
