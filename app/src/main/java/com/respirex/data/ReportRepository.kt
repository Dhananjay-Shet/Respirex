package com.respirex.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime

object ReportRepository {

    private val _report: MutableLiveData<List<Report>?>? = MutableLiveData()
    val report: LiveData<List<Report>?>? = _report

    private val _value = MutableLiveData<String>("Loading report...")
    val value: LiveData<String> = _value

    private var _patient: Patient? = null

    fun setPatient(patient: Patient?) {
        this._patient = patient
    }

    fun getDisease(): String {
        return _patient?.disease.toString()
    }

    fun addReport(result: String, value: (String) -> Unit) {
        if (_patient != null) {
            val currentDateTime = LocalDateTime.now()
            val day = currentDateTime.dayOfMonth
            val month = currentDateTime.monthValue
            val year = currentDateTime.year
            val hour = currentDateTime.hour
            val minute = currentDateTime.minute
            val second = currentDateTime.second

            val date = "$day/$month/$year"
            val time = "$hour:$minute:$second"

            val addReport = Report(_patient!!, result, date, time)
            Firebase.addReport(addReport, value = { value ->
                if (value == "Success") {
                    fetchReport()
                    value(value)
                } else {
                    value(value)
                }
            })
        }
    }

    fun fetchReport() {
        Firebase.fetchReport { value, reportList ->
            _report?.value = reportList
            _value.value = value
        }
    }

    fun setValueToInitial() {
        _value.value = "Loading report..."
        _patient = null
        _report?.value = null
    }

}