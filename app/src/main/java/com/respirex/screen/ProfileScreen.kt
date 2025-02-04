package com.respirex.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.respirex.data.Firebase

@Composable
fun ProfileScreen() {

    val currentUser = Firebase.getCurrentUser()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Profile",
            modifier = Modifier.padding(top = 50.dp),
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Image(
            painter = rememberAsyncImagePainter(currentUser?.photoUrl),
            contentDescription = "Profile",
            modifier = Modifier
                .padding(top = 20.dp)
                .size(150.dp)
                .clip(CircleShape),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop,
        )

        OutlinedTextField(
            value = currentUser?.displayName ?: "",
            onValueChange = {

            },
            readOnly = true,
            singleLine = true,
            label = { Text("Name", color = MaterialTheme.colorScheme.onPrimary) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .width(250.dp)
                .padding(top = 10.dp),
        )

        OutlinedTextField(
            value = currentUser?.email ?: "",
            onValueChange = {

            },
            readOnly = true,
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
            ),
            modifier = Modifier
                .padding(top = 20.dp)
                .width(300.dp),
        )
        Spacer(Modifier.height(25.dp))

    }
}