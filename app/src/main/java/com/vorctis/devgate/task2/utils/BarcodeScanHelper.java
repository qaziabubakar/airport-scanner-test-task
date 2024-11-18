package com.vorctis.devgate.task2.utils;

import android.content.Context;

import com.scandit.datacapture.barcode.capture.BarcodeCapture;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureListener;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSession;
import com.scandit.datacapture.barcode.capture.BarcodeCaptureSettings;
import com.scandit.datacapture.barcode.data.Barcode;
import com.scandit.datacapture.barcode.data.Symbology;
import com.scandit.datacapture.barcode.ui.overlay.BarcodeCaptureOverlay;
import com.scandit.datacapture.barcode.ui.overlay.BarcodeCaptureOverlayStyle;
import com.scandit.datacapture.core.capture.DataCaptureContext;
import com.scandit.datacapture.core.source.Camera;
import com.scandit.datacapture.core.source.FrameSourceState;
import com.scandit.datacapture.core.ui.DataCaptureView;
import com.scandit.datacapture.core.ui.style.Brush;
import com.scandit.datacapture.core.ui.viewfinder.RectangularViewfinder;
import com.scandit.datacapture.core.ui.viewfinder.RectangularViewfinderStyle;

import java.util.HashSet;

public class BarcodeScanHelper {

    private DataCaptureContext dataCaptureContext;
    private BarcodeCapture barcodeCapture;
    private Camera camera;
    private DataCaptureView dataCaptureView;

    private BarcodeCaptureListener barcodeCaptureListener;
    private BarcodeScanCallback callback;

    public interface BarcodeScanCallback {
        void onBarcodeScanned(String data, String symbology);
    }

    public BarcodeScanHelper(Context context, String licenseKey, BarcodeScanCallback callback) {
        this.callback = callback;
        dataCaptureContext = DataCaptureContext.forLicenseKey(licenseKey);
        camera = Camera.getDefaultCamera(BarcodeCapture.createRecommendedCameraSettings());
        if (camera != null) {
            dataCaptureContext.setFrameSource(camera);
        }
        BarcodeCaptureSettings barcodeCaptureSettings = new BarcodeCaptureSettings();
        HashSet<Symbology> symbologies = new HashSet<>();
        symbologies.add(Symbology.QR);
        barcodeCaptureSettings.enableSymbologies(symbologies);

        barcodeCapture = BarcodeCapture.forDataCaptureContext(dataCaptureContext, barcodeCaptureSettings);
        barcodeCaptureListener = new BarcodeCaptureListener() {
            @Override
            public void onBarcodeScanned(BarcodeCapture capture, BarcodeCaptureSession session, com.scandit.datacapture.core.data.FrameData frameData) {
                Barcode barcode = session.getNewlyRecognizedBarcode();
                if (barcode != null) {
                    String symbology = barcode.getSymbology().name();
                    String data = barcode.getData();
                    if (callback != null) {
                        callback.onBarcodeScanned(data, symbology);
                    }
                }
            }

            @Override
            public void onSessionUpdated(BarcodeCapture capture, BarcodeCaptureSession session, com.scandit.datacapture.core.data.FrameData frameData) {
            }

            @Override
            public void onObservationStarted(BarcodeCapture capture) {
            }

            @Override
            public void onObservationStopped(BarcodeCapture capture) {
            }
        };

        barcodeCapture.addListener(barcodeCaptureListener);
        dataCaptureView = DataCaptureView.newInstance(context, dataCaptureContext);
        BarcodeCaptureOverlay overlay = BarcodeCaptureOverlay.newInstance(
                barcodeCapture,
                dataCaptureView,
                BarcodeCaptureOverlayStyle.FRAME
        );
        overlay.setViewfinder(new RectangularViewfinder(RectangularViewfinderStyle.SQUARE));
        Brush brush = new Brush(android.graphics.Color.TRANSPARENT, android.graphics.Color.WHITE, 3f);
        overlay.setBrush(brush);
    }

    public DataCaptureView getDataCaptureView() {
        return dataCaptureView;
    }

    public void startScanning() {
        if (camera != null) {
            camera.switchToDesiredState(FrameSourceState.ON, null);
        }
        barcodeCapture.setEnabled(true);
    }

    public void stopScanning() {
        barcodeCapture.setEnabled(false);
        if (camera != null) {
            camera.switchToDesiredState(FrameSourceState.OFF, null);
        }
    }

    public void release() {
        barcodeCapture.removeListener(barcodeCaptureListener); // Use the stored listener
        dataCaptureContext.removeMode(barcodeCapture);
    }
}


