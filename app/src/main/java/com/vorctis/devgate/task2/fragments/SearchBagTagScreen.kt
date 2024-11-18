package com.vorctis.devgate.task2.fragments

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vorctis.devgate.task2.R
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.content.ContextCompat
import com.vorctis.devgate.task2.utils.Routes
import kotlinx.coroutines.launch

class SearchBagTagScreen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun content(navController: NavHostController) {

        val context = LocalContext.current
        var cameraPermissionGranted by remember { mutableStateOf(false) }

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                cameraPermissionGranted = isGranted
                if (isGranted) {
                    navController.navigate(Routes.ScannerScreen.route)
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Camera permission is required to proceed.")
                    }
                }
            }
        )

        var scannedData by remember { mutableStateOf("") }
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        LaunchedEffect(savedStateHandle) {
            savedStateHandle?.get<String>("scannedData")?.let { result ->
                scannedData = result
                savedStateHandle.remove<String>("scannedData")
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Search bag tag",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle menu action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_menu),
                                contentDescription = "Menu",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFF000028)
                    )
                )
            },
            containerColor = Color(0xFF000028)
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(0xFF00CCCC)),
                    color = Color(0xFF23233C)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Enter Bag Tag # to search for",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF37374D))
                        ) {
                            TextField(
                                value = scannedData,
                                onValueChange = { /* Handle value change */ },
                                placeholder = {
                                    Text(
                                        text = "Type here",
                                        color = Color(0xFFA6A6A6)
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.Transparent,
                                    textColor = Color.White,
                                    cursorColor = Color(0xFF00CCCC),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                            )
                            Divider(
                                color = Color.White.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                            )
                            IconButton(onClick = {
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    navController.navigate(Routes.ScannerScreen.route)
                                } else {
                                    permissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.qr_code),
                                    contentDescription = "QR Code",
                                    tint = Color(0xFF00FFB9),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Search results will appear here",
                        color = Color(0xFFA6A6A6),
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
