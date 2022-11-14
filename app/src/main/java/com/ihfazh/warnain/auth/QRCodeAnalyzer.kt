package com.ihfazh.warnain.auth

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage class QRCodeAnalyzer(
    private val onCodeFound: (String) -> Unit
) : ImageAnalysis.Analyzer {
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null){
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val scanner = BarcodeScanning.getClient()
            scanner.process(image).addOnSuccessListener {
                it.forEach { barcode -> if (barcode.rawValue != null ) onCodeFound.invoke(barcode.rawValue!!) }
                imageProxy.close()
            }

        }
    }
}