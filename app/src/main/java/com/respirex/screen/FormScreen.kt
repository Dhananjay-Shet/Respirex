package com.respirex.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.respirex.viewmodel.FormViewmodel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(image: () -> Unit) {

    val viewmodel: FormViewmodel = viewModel()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    var bloodGrpMenu = rememberSaveable { mutableStateOf(false) }
    var diseaseMenu = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            "Patient Form",
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 50.dp).width(LocalConfiguration.current.screenWidthDp.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
        ) {
            Text(
                "Enter patient details below",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .width(350.dp),
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(350.dp)
            ) {
                Text(
                    "First Name :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.Bold,
                )
                Column(horizontalAlignment = Alignment.Start) {
                    OutlinedTextField(
                        value = viewmodel.firstname.observeAsState("").value,
                        onValueChange = {
                            viewmodel.updateFirstname(it)
                            viewmodel.validateFirstname(it)
                        },
                        isError = viewmodel.firstnameError.observeAsState("").value.isNotEmpty(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorBorderColor = MaterialTheme.colorScheme.onError,
                            errorTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorCursorColor = MaterialTheme.colorScheme.onPrimary,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onError
                        ),
                        supportingText = {
                            if (viewmodel.firstnameError.observeAsState("").value.isNotEmpty()) {
                                Text(
                                    text = viewmodel.firstnameError.observeAsState("").value,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                    )

                }
            }

            Row(
                modifier = Modifier
                    .width(350.dp)
            ) {
                Text(
                    "Last Name :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.Bold
                )
                Column(horizontalAlignment = Alignment.Start) {
                    OutlinedTextField(
                        value = viewmodel.lastname.observeAsState("").value,
                        onValueChange = {
                            viewmodel.updateLastname(it)
                            viewmodel.validateLastname(it)
                        },
                        isError = viewmodel.lastnameError.observeAsState("").value.isNotEmpty(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorBorderColor = MaterialTheme.colorScheme.onError,
                            errorTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorCursorColor = MaterialTheme.colorScheme.onPrimary,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onError
                        ),
                        supportingText = {
                            if (viewmodel.lastnameError.observeAsState("").value.isNotEmpty()) {
                                Text(
                                    text = viewmodel.lastnameError.observeAsState("").value,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )

                }
            }

            Row(
                modifier = Modifier
                    .width(350.dp)
            ) {
                Text(
                    "Gender :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(100.dp),
                    fontWeight = FontWeight.Bold
                )
                Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewmodel.isMale.observeAsState(true).value,
                        onClick = { viewmodel.updateToMale() },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                    Text("Male", color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
                    RadioButton(
                        selected = !viewmodel.isMale.observeAsState(true).value,
                        onClick = { viewmodel.updateToFemale() },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text("Female", color = MaterialTheme.colorScheme.onPrimary, fontSize = 20.sp)
                }
            }

            Row(
                modifier = Modifier
                    .width(350.dp)
            ) {
                Text(
                    "DOB : ",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _: DatePicker, bornYear: Int, bornMonth: Int, bornDay: Int ->
                                val selectedDate = Calendar.getInstance()
                                selectedDate.set(bornYear, bornMonth, bornDay)

                                val today = Calendar.getInstance()
                                if (!selectedDate.before(today)) {
                                    Toast.makeText(context, "Choose a valid date before today", Toast.LENGTH_SHORT).show()
                                } else {
                                    viewmodel.updateDOB("$bornDay/${bornMonth + 1}/$bornYear")
                                    val age = if (currentMonth > bornMonth.toInt()) {
                                        currentYear - bornYear.toInt()
                                    } else if (currentMonth == bornMonth.toInt()) {
                                        if (currentDay >= bornDay.toInt()) {
                                            currentYear - bornYear.toInt()
                                        } else {
                                            currentYear - bornYear.toInt() - 1
                                        }
                                    } else {
                                        currentYear - bornYear.toInt() - 1
                                    }
                                    viewmodel.updateAge(age)
                                }
                            },
                            currentYear,
                            currentMonth-1,
                            currentDay,
                        ).show()
                    }, modifier = Modifier.width(50.dp)
                ) {
                    Icon(
                        Icons.Filled.CalendarMonth,
                        contentDescription = "DOB",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    viewmodel.dob.observeAsState().value ?: "Select a date",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .width(150.dp),
                )
            }

            Row(
                modifier = Modifier
                    .width(350.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Blood Grp :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .width(120.dp),
                    fontWeight = FontWeight.Bold
                )
                ExposedDropdownMenuBox(
                    expanded = bloodGrpMenu.value,
                    onExpandedChange = { bloodGrpMenu.value = it },
                ) {
                    OutlinedTextField(
                        value = viewmodel.bloodGrp.observeAsState("").value,
                        onValueChange = {},
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .width(100.dp)
                            .height(50.dp),
                        readOnly = true,
                        singleLine = true,
                        trailingIcon = { Icon(Icons.Filled.ArrowDropDown, "Blood Group") },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                        ),
                    )
                    ExposedDropdownMenu(
                        expanded = bloodGrpMenu.value,
                        onDismissRequest = { bloodGrpMenu.value = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("A+", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("A+")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("A-", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("A-")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("B+", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("B+")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("B-", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("B-")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("AB+", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("AB+")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("AB-", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("AB-")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("O+", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("O+")
                                bloodGrpMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("O-", color = MaterialTheme.colorScheme.onPrimary) },
                            onClick = {
                                viewmodel.updateBloodGrp("O-")
                                bloodGrpMenu.value = false
                            },
                        )
                    }
                }

            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(350.dp)
            ) {
                Text(
                    "Mobile No :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.Bold,
                )
                Column(horizontalAlignment = Alignment.Start) {
                    OutlinedTextField(
                        value = viewmodel.mobileNumber.observeAsState("").value,
                        onValueChange = {
                            viewmodel.updateMobileNumber(it)
                            viewmodel.validateMobileNumber(it)
                        },
                        isError = viewmodel.mobileNumberError.observeAsState("").value.isNotEmpty(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorBorderColor = MaterialTheme.colorScheme.onError,
                            errorTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorCursorColor = MaterialTheme.colorScheme.onPrimary,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onError
                        ),
                        supportingText = {
                            if (viewmodel.mobileNumberError.observeAsState("").value.isNotEmpty()) {
                                Text(
                                    text = viewmodel.mobileNumberError.observeAsState("").value,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )

                }
            }

            Row(
                modifier = Modifier
                    .width(350.dp)
            ) {
                Text(
                    "Symptoms :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.Bold,
                )
                Column(horizontalAlignment = Alignment.Start) {
                    OutlinedTextField(
                        value = viewmodel.symptoms.observeAsState("").value,
                        onValueChange = {
                            viewmodel.updateSymptoms(it)
                            viewmodel.validateSymptoms(it)
                        },
                        isError = viewmodel.symptomsError.observeAsState("").value.isNotEmpty(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.onPrimary,
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorBorderColor = MaterialTheme.colorScheme.onError,
                            errorTextColor = MaterialTheme.colorScheme.onPrimary,
                            errorCursorColor = MaterialTheme.colorScheme.onPrimary,
                            errorSupportingTextColor = MaterialTheme.colorScheme.onError
                        ),
                        supportingText = {
                            if (viewmodel.symptomsError.observeAsState("").value.isNotEmpty()) {
                                Text(
                                    text = viewmodel.symptomsError.observeAsState("").value,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .width(350.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Disease :",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .width(120.dp),
                    fontWeight = FontWeight.Bold
                )
                ExposedDropdownMenuBox(
                    expanded = diseaseMenu.value,
                    onExpandedChange = { diseaseMenu.value = it },
                ) {
                    OutlinedTextField(
                        value = viewmodel.disease.observeAsState("").value,
                        onValueChange = {},
                        modifier = Modifier
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                            .width(180.dp)
                            .height(60.dp),
                        readOnly = true,
                        singleLine = true,
                        trailingIcon = { Icon(Icons.Filled.ArrowDropDown, "Blood Group") },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                        ),
                    )
                    ExposedDropdownMenu(
                        expanded = diseaseMenu.value,
                        onDismissRequest = { diseaseMenu.value = false },
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Covid-19",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            onClick = {
                                viewmodel.updateDisease("Covid-19")
                                diseaseMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Lung cancer",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            onClick = {
                                viewmodel.updateDisease("Lung cancer")
                                diseaseMenu.value = false
                            },
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Tuberculosis",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            onClick = {
                                viewmodel.updateDisease("Tuberculosis")
                                diseaseMenu.value = false
                            },
                        )
                    }
                }
            }

            Button(
                onClick = {
                    if (viewmodel.isValid()) {
                        viewmodel.patientObj()
                        image()
                    } else {
                        Toast.makeText(context, "Fill details properly", Toast.LENGTH_SHORT).show()
                    }
                }, modifier = Modifier
                    .padding(top = 25.dp)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Submit Form")
            }

        }
    }
}

