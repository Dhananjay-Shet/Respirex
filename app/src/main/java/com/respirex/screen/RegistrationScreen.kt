package com.respirex.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.respirex.viewmodel.RegistrationViewmodel

@Composable
fun RegistrationScreen(home: () -> Unit) {
    val context = LocalContext.current

    val viewmodel: RegistrationViewmodel = viewModel()

    var loading = rememberSaveable { mutableStateOf(false) }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Register",
                modifier = Modifier.padding(top = 50.dp),
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Image(
                painter = rememberAsyncImagePainter(viewmodel.painterResource.observeAsState().value),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .clickable { },
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
            )

            OutlinedTextField(
                value = viewmodel.name.observeAsState("").value,
                onValueChange = {
                    viewmodel.updateName(it)
                    viewmodel.validateName(it)
                },
                isError = viewmodel.nameError.observeAsState("").value.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                label = { Text("Name", color = MaterialTheme.colorScheme.onPrimary) },
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
                modifier = Modifier.width(200.dp),
                supportingText = {
                    if (viewmodel.nameError.observeAsState("").value.isNotBlank()) {
                        Text(
                            text = viewmodel.nameError.observeAsState("").value,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )

            OutlinedTextField(
                value = viewmodel.email.observeAsState("").value,
                onValueChange = {
                    viewmodel.updateEmail(it)
                    viewmodel.validateEmail(it)
                },
                isError = viewmodel.emailError.observeAsState("").value.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email
                ),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = "Email",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                singleLine = true,
                label = { Text("Email", color = MaterialTheme.colorScheme.onPrimary) },
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
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(300.dp),
                supportingText = {
                    if (viewmodel.emailError.observeAsState("").value.isNotEmpty()) {
                        Text(
                            text = viewmodel.emailError.observeAsState("").value,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )



            OutlinedTextField(
                value = viewmodel.password.observeAsState("").value,
                onValueChange = {
                    viewmodel.updatePassword(it)
                    viewmodel.validatePassword(it)
                },
                isError = viewmodel.passwordError.observeAsState("").value.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                label = { Text("Password", color = MaterialTheme.colorScheme.onPrimary) },
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
                modifier = Modifier.width(300.dp),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Password,
                        contentDescription = "Password",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(
                            imageVector = if (passwordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (passwordVisible.value) "Hide Password" else "Show Password",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                supportingText = {
                    if (viewmodel.passwordError.observeAsState("").value.isNotEmpty()) {
                        Text(
                            text = viewmodel.passwordError.observeAsState("").value,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )


            Button(
                onClick = {
                    loading.value = true
                    viewmodel.registerUser { result ->
                        loading.value = false
                        if (result == "Success") {
                            viewmodel.fetchReport()
                            Toast.makeText(
                                context,
                                "Account created successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            home()
                        } else Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .padding(top = 25.dp)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Create account")
            }
            Spacer(modifier = Modifier.height(25.dp))
        }
        if (loading.value) {
            CircularProgressIndicator(
                modifier = Modifier.width(50.dp),
                color = Color.Gray,
                trackColor = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

