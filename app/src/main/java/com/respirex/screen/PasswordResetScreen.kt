package com.respirex.screen

import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.respirex.viewmodel.PasswordResetViewmodel

@Composable
fun PasswordResetScreen() {

    val viewmodel: PasswordResetViewmodel = viewModel()
    val context = LocalContext.current
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            Icons.Default.Lock,
            contentDescription = "Lock",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 50.dp).size(40.dp)
        )
        Text(
            "Forgot your password?",
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Column(horizontalAlignment = Alignment.Start) {
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
                        Icons.Default.Email,
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

            Button(
                onClick = {
                   viewmodel.resetPassword { bool,result ->
                       Toast.makeText(context, result, Toast.LENGTH_LONG).show()
                       if(bool){
                           dispatcher?.onBackPressed()
                       }
                   }
                },
                modifier = Modifier
                    .width(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Reset")
            }
        }
    }
}