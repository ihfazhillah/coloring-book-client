package com.ihfazh.warnain.auth

import android.Manifest
import android.annotation.SuppressLint
import android.util.Rational
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.*
import com.ihfazh.warnain.R
import com.ihfazh.warnain.ui.theme.WarnainTheme


@Composable
@OptIn(ExperimentalGetImage::class)
fun CameraView(
    onCodeFound: (String) -> Unit = {}
){
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)
            cameraFuture.addListener({
                val cameraProvider = cameraFuture.get()

                // preview ini salah satu camera use case
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                // analyzer
                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setTargetResolution(Size(250, 250))
                    .build()

                analysis.setAnalyzer(executor, QRCodeAnalyzer{
                    onCodeFound.invoke(it)
                })

                // viewPort
                val viewPort = ViewPort.Builder(Rational(250, 250), previewView.display.rotation)
                    .setScaleType(ViewPort.FILL_CENTER)
                    .build()


                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()

                val useCaseGroup = UseCaseGroup.Builder()
                    .addUseCase(preview)
                    .addUseCase(analysis)
                    .setViewPort(viewPort)
                    .build()

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    useCaseGroup
                )

            }, executor)


            previewView
        },
            modifier = Modifier
                .width(250.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Scan QRCode yang ada di aplikasi komputer untuk masuk",
            fontWeight = MaterialTheme.typography.body2.fontWeight,
            textAlign = TextAlign.Center,
            fontSize = MaterialTheme.typography.body2.fontSize,
            color = MaterialTheme.colors.background
        )

    }

}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoCameraView(
    cameraPermission: PermissionState
){
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val textToShow = if (cameraPermission.status.shouldShowRationale) {
                "The camera is important for this app. Please grant the permission."
            } else {
                "Camera not available"
            }

            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colors.background)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(16.dp),
                text = textToShow,
                fontWeight = MaterialTheme.typography.body2.fontWeight,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.body2.fontSize,
                color = MaterialTheme.colors.background
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { cameraPermission.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QRCodeServerConfig(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onCodeFound: (String) -> Unit = {}
) {
    // permission handling
    val cameraPermission = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    WarnainTheme {
        Scaffold(
            contentColor = MaterialTheme.colors.onPrimary,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Scan Server",
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = MaterialTheme.typography.h6.fontWeight,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    },
                    contentColor = MaterialTheme.colors.onPrimary,
                    navigationIcon = {
                        IconButton(onClick = { onBack.invoke() }) {
                            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24), contentDescription = "Go Back")
                        }
                    }
                )
            }
        ) {
            if (cameraPermission.status.isGranted) {
                CameraView(onCodeFound)
            } else {
                NoCameraView(cameraPermission = cameraPermission)
            }
        }
    }


}


@androidx.compose.ui.tooling.preview.Preview
@Composable
fun CameraPreview(){
    QRCodeServerConfig(onBack = {})

}