package com.vorctis.devgate.task2.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.core.content.ContextCompat
import com.vorctis.devgate.task2.R
import com.vorctis.devgate.task2.ui.materialcomponents.BagTagItem
import com.vorctis.devgate.task2.utils.Routes
import kotlinx.coroutines.launch
import org.json.JSONArray

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

        // Initialize persistent list of QR codes
        var qrCodeList by remember { mutableStateOf(loadScannedValues(context)) }

        LaunchedEffect(savedStateHandle) {
            savedStateHandle?.get<String>("scannedData")?.let { result ->
                if (result.isNotEmpty() && !qrCodeList.contains(result)) {
                    qrCodeList = qrCodeList + result
                    saveScannedValues(context, qrCodeList)
                }
                scannedData = result
                savedStateHandle.remove<String>("scannedData")
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Search Bag Tag",
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
                    Column(modifier = Modifier.padding(12.dp)) {
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
                                onValueChange = { scannedData = it },
                                placeholder = {
                                    Text(text = "Type here", color = Color(0xFFA6A6A6))
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
                                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("scannedData")
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

                Text(
                    text = "Search results: ${qrCodeList.size}",
                    color = Color(0xFFA6A6A6),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn {
                    items(qrCodeList.size) { index ->
                        BagTagItem(qrCode = qrCodeList[index],
                            onDelete = {
                                val updatedList = qrCodeList.toMutableList().apply { removeAt(index) }
                                qrCodeList = updatedList
                                saveScannedValues(context, updatedList)

                                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("scannedData")
                            },
                            onNext = { selectedLocation ->
                                navController.currentBackStackEntry?.savedStateHandle?.set("selectedLocation", selectedLocation)
                                navController.navigate(Routes.LocationSelectedScreen.route)
                            },
                        )
                    }
                }

            }
        }
    }

    private fun loadScannedValues(context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences("qr_storage", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("scanned_values", "[]")
        val jsonArray = JSONArray(jsonString)
        return List(jsonArray.length()) { jsonArray.getString(it) }
    }

    private fun saveScannedValues(context: Context, values: List<String>) {
        val sharedPreferences = context.getSharedPreferences("qr_storage", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val jsonArray = JSONArray(values)
        editor.putString("scanned_values", jsonArray.toString())
        editor.apply()
    }
}
