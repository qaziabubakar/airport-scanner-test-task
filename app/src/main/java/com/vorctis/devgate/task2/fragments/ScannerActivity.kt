package com.vorctis.devgate.task2.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.vorctis.devgate.task2.utils.BarcodeScanHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val SCANDIT_KEY =
    "ArvFMBOfNtXhGsHP1cmiEA85n3IiQdKsex8bja0mHaG3T7shcmjxywZbQvOWZJeWx3oTEkJ22b8iEDfVFQuka3ZDPYKZRJARby6SAC15SqZyLsj2uVRfXkl2hE1CeSDxBW79pdRGg6RrD1mfNA+w1Chu7QvSGVcrhCJHkU84f+nJBqB/xEyVjbAkqY/TE9JK+xPcLKtgRGBWPHblwz917Oof0rAHAuH0BWhkcd0IK5klMXHi3QNb7HYZDqc5Ar9U5G0lRkMuxdYTN+euyW9unzkBOuk+H9ZWrACKfBpfWVkJSGA/E0Ihzio8es6YbU8cUAbgVDJQBS2hecisUlxghZ8af8G6GT047wb/6mcqw16RHCSwLR90ya0o7dRUN2aPeknpFiFmk7etQ0X+WGAaBZpz/fWceUUzqH/fSjxzPMhBdBR+O2dU1JBWcpqYae7pJxqF3d5aHUV4ZzMkE0+aXyxx4f8yf3gL5Wc8M1xcXUqjQgDGnEJVf+x4HrYZSCMOmG+xi3ZGvCqjaQgJmVM7TkNOC0qmd41A9l2Rat1lEpEGSh4SbHtWZYt2FQisaG9jY3KqtMVA/nvzb2wkBnA3hSpwLB5tVYPlrn3IvxtxDnP/dCQuj3P1BlorfgKbfmn8hxa58IZayKNDQ1L2j1CdQogcmoDMfBLuOGuwDPtWSXY9UXv8wUKPHhoaz1RGY5sZ6FA8RXNjXT2IZXIsV3s+mqAtnxGSIsl6vXTOl5lKKE5YFc0Ti2y8SBZ2yLeyTMXqwHaFocN+9BouJYJEfk4i2TVM8YgIEuHSen0Z6xAqziGRRGTdIBJUUANjx6DdV/gn0AwPUuxe6maLXdLUpkKTDeEAmVxkU9fgZW63XwREa62gMLT8jwwzQxYAiBHdYDG/DHZKmyUX3dvyY24eIRd1BEx7f7zLS4a/oi+zbPsPiqzOMxan5gz+um0swS2afdp1TnaylN9XVNnBU4jvpWx2EOpBnARPWAJEz3hgygN6V1wbRF+2zi1u4gQua2EbGDj8YkYUs8hz07wNFx0G0S3+iMxxg5+da8lFeHXHVrJMpG6Da/tzr24FQdBXeR1fYJitMW75VqoNbadWJ5WOQBNrsDkuEPsVUvumuhczi7pPTK9iXb3csGsuaX9NViYhWu6reVA6gKt0OxBPaEYmO1+joLxnA1I5NKZttXzRj7YqqcUOKJyaH26hNZwGtuEca9nhgVA84YJI3QtlQWk3Qnwf2ytkqCxbW8xkXhXuZtmRWOBikHPEkI43lqQw/d7VkxhYeM+lt4uJUdQ5BMikKKbywIgEnotDNyPHmvbztimPmQz/b5ms++3soneLuMfcXiYXWLfYmluA/ioqhA4djxPJZ8gSXdLTEtRC9VB1G28u2QMuf/2H3Cb0y4avZaT94XJL0p6BI41+BvHyW7a+fZVxYYvO87a8BSnHv1vNiXR0hbbzY8Za3jnZu8itKs7yeP5Dz9XtrpztziDDCOS6Ka9mTwPJQ+bnyiRvzS1x75QBCQqLtQPurYybYsKh295p20QXxzEMhhK8Oa8U/GmBkJ9qGPFa92K1hsezTjiNAyQeHeM/sE8eMvTyNDwL/HOEwSLUpZec1CvT1mlTgql4nCmYNml/0woyRfJREsi8XO2PJRTQQlN58RvboWUw1vXpszG/StR+IPRoKqFpJA538RUb/bIgV7mL2UJyOgifNOu9RGwcpZm9HLfL0J83gySzXMR8fAzAQ5CHAXvdcCkClSFpKe36RVZQkAvMdVa5IQKLF+/CgwJoZDehNCmhKPSvnKpesG+1Uosyd6A0OmW4R8wR6+E+K5RKS6sCIDipjtVWhmI3ZyGehHQT3zvFa45ofN336BAD4RAXxUKIqP5CK+nZ0TL5aZYdl4r2y37+li2P/1ir4lUImfCBJX2zCubmjM8zMpOJ9qQECgDyUmWi"

class ScannerActivity {

    @Composable
    fun content(navController: NavHostController) {
        val context = LocalContext.current
        var isPermissionGranted by remember { mutableStateOf(false) }
        var isScanning by remember { mutableStateOf(true) } // Control scanning state

        val barcodeScanHelper = remember {
            BarcodeScanHelper(context, SCANDIT_KEY) { data, symbology ->
                if (data != null && isScanning) {
                    isScanning = false // Stop further scans
                    Log.d("Scanner", "Scanned Barcode: $data (Symbology: $symbology)")

                    CoroutineScope(Dispatchers.Main).launch {
                        navController.previousBackStackEntry?.savedStateHandle?.set("scannedData", data)
                        navController.popBackStack() // Go back with the result
                    }
                }
            }
        }

        // Permission launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                isPermissionGranted = granted
                if (granted) {
                    barcodeScanHelper.startScanning()
                } else {
                    println("Permission denied")
                }
            }
        )

        LaunchedEffect(Unit) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                isPermissionGranted = true
                barcodeScanHelper.startScanning()
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isPermissionGranted) {
                AndroidView(
                    factory = { barcodeScanHelper.getDataCaptureView() },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

}
