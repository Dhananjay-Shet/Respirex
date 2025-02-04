package com.respirex.screen

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.respirex.viewmodel.ImageCNNViewmodel

@Composable
fun ImageScreen(report: (Int) -> Unit) {

    val context = LocalContext.current
    val viewmodel: ImageCNNViewmodel = viewModel()
    val imageUri = viewmodel.imageUri.observeAsState().value
    var loading = rememberSaveable { mutableStateOf(false) }
    var opacity = rememberSaveable { mutableFloatStateOf(0.25f) }

    val getResultForGallery =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val imageUri = data?.data!!
                viewmodel.setImageUri(imageUri)
                opacity.floatValue = 1f
            }
        }

    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(horizontalAlignment = Alignment.Start){
                Image(
                    painter = rememberAsyncImagePainter(imageUri?:Uri.parse("android.resource://com.respirex/drawable/sample_chest")),
                    contentDescription = "Image",
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .height(300.dp)
                        .width(300.dp)
                        .border(1.dp, MaterialTheme.colorScheme.onPrimary)
                        .alpha(opacity.floatValue)
                )
                Text("Please upload only chest or lung scan images",color = MaterialTheme.colorScheme.onError, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    getResultForGallery.launch(intent)
                }, modifier = Modifier
                    .padding(top = 10.dp)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Choose image from gallery", color = MaterialTheme.colorScheme.onPrimary)
            }
            Button(
                onClick = {
                    if (imageUri != null) {
                        viewmodel.generateResult(context)
                        viewmodel.addReport(value = { status ->
                            if (status == "Success") {
                                Toast.makeText(context, "Report generated", Toast.LENGTH_SHORT)
                                    .show()
                                report(-1)
                            } else {
                                Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else {
                        Toast.makeText(context, "Please upload scan image", Toast.LENGTH_SHORT).show()
                    }
                }, modifier = Modifier
                    .padding(top = 20.dp)
                    .width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Generate Report", color = MaterialTheme.colorScheme.onPrimary)
            }
            Spacer(modifier = Modifier.height(50.dp))
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