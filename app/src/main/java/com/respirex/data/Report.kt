package com.respirex.data

data class Report(
    val patient: Patient = Patient(),
    val result: String = "",
    val date: String = "",
    val time: String =""
)
