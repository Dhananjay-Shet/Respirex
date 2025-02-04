package com.respirex.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.respirex.MainActivity
import com.respirex.R
import com.respirex.viewmodel.HomeViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    profile: () -> Unit,
    logout: () -> Unit,
    form: () -> Unit,
    history: () -> Unit,
    symptoms: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as MainActivity

    val viewmodel: HomeViewmodel = viewModel()

    val currentUser = viewmodel.getCurrentUser()
    val displayName = currentUser?.displayName.toString()
    val photoUrl = currentUser?.photoUrl

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val configuration = LocalConfiguration.current
    val isLandscape =
        (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val isPlaying = viewmodel.isPlaying.observeAsState().value

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            // Handle lifecycle events here
            when (event) {
                Lifecycle.Event.ON_CREATE -> {

                }

                Lifecycle.Event.ON_START -> {

                }

                Lifecycle.Event.ON_RESUME -> {

                }

                Lifecycle.Event.ON_PAUSE -> {

                }

                Lifecycle.Event.ON_STOP -> {
                    viewmodel.stopAudio()
                }

                Lifecycle.Event.ON_DESTROY -> {

                }

                else -> Unit
            }
        })
    }

    BackHandler {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            activity.finish()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = if (isLandscape) Modifier
                    .width(screenWidth * 0.45f)
                    .height(screenHeight)
                else Modifier.width(screenWidth * 0.8f),
                drawerContainerColor = MaterialTheme.colorScheme.background,
                drawerContentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(photoUrl),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                    Text(
                        text = displayName,
                        fontSize = 15.sp
                    )
                    Text(
                        "Profile",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .height(30.dp)
                            .width(screenWidth)
                            .clickable { profile() },
                        fontSize = 20.sp
                    )
                    Text(
                        "Report History", color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .height(30.dp)
                            .width(screenWidth)
                            .clickable { history() },
                        fontSize = 20.sp
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 20.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Logout", color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .height(30.dp)
                            .width(screenWidth)
                            .clickable {
                                viewmodel.logoutUser(context) {
                                    Toast
                                        .makeText(context, "Logged out", Toast.LENGTH_SHORT)
                                        .show()
                                    logout()
                                }
                            },
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                }
            }
        },
        gesturesEnabled = true,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    title = {
                        Text(
                            "Respirex",
                            modifier = Modifier.padding(start = 5.dp),
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                            viewmodel.stopAudio()
                        }, modifier = Modifier.padding(start = 10.dp)) {
                            Icon(
                                painter = rememberAsyncImagePainter(currentUser?.photoUrl),
                                contentDescription = "Profile Icon",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape),
                                tint = Color.Unspecified,
                            )
                        }
                    }
                )
            },
            floatingActionButton = {FloatingActionButton(
                onClick = {
                    if (isPlaying == false) {
                        viewmodel.startAudio()
                        viewmodel.toggle()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            ) {
                if (isPlaying == true) {
                    Icon(painter = painterResource(id = R.drawable.volume_up), "Volume")
                } else {
                    Icon(Icons.Filled.Info, "Information")
                }
            }},
            floatingActionButtonPosition = FabPosition.End,
            content = { innerPadding ->
                if (isLandscape) {
                    Column {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            SingleCardItem(
                                R.drawable.symptom,
                                "Check symptoms",
                                null
                            ) { symptoms() }
                            SingleCardItem(
                                R.drawable.test,
                                "Conduct Test",
                                ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
                            ) { form() }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(25.dp))
                        SingleCardItem(
                            R.drawable.symptom,
                            "Check symptoms",
                            null
                        ) { symptoms() }
                        Spacer(modifier = Modifier.height(25.dp))
                        SingleCardItem(
                            R.drawable.test,
                            "Conduct Test",
                            ColorFilter.tint(MaterialTheme.colorScheme.surfaceVariant)
                        ) { form() }
                        Spacer(modifier = Modifier.height(25.dp))
                    }
                }
            }
        )
    }
}

@Composable
fun SingleCardItem(
    painterResource: Int,
    text: String,
    colorFilter: ColorFilter?,
    onCLick: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .clickable { onCLick() }
            .size(250.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = painterResource),
                contentDescription = text,
                modifier = Modifier
                    .size(200.dp),
                colorFilter = colorFilter
            )
            Text(
                text,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }

}
