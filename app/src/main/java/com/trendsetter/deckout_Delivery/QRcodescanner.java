package com.trendsetter.deckout_Delivery;

import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;


public class QRcodescanner  extends AppCompatActivity implements
        DecoratedBarcodeView.TorchListener {



        private CaptureManager capture;
        private DecoratedBarcodeView barcodeScannerView;
        private ImageButton switchFlashlightButton;
        private ViewfinderView viewfinderView;
        Boolean chkflashonoff = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.qrscannerlayout);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            barcodeScannerView = (DecoratedBarcodeView)findViewById(R.id.zxing_barcode_scanner);
            barcodeScannerView.setTorchListener(this);

            switchFlashlightButton = (ImageButton) findViewById(R.id.switch_flashlight);

            viewfinderView = (ViewfinderView) findViewById(R.id.zxing_viewfinder_view);

            if (!hasFlash()) {
                switchFlashlightButton.setVisibility(View.GONE);
            }

            capture = new CaptureManager(this, barcodeScannerView);
            capture.initializeFromIntent(getIntent(), savedInstanceState);
            capture.decode();

        }

        @Override
        protected void onResume() {
            super.onResume();
            capture.onResume();
        }

        @Override
        protected void onPause() {
            super.onPause();
            capture.onPause();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            capture.onDestroy();
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            capture.onSaveInstanceState(outState);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
        }

        /**
         * Check if the device's camera has a Flashlight.
         * @return true if there is Flashlight, otherwise false.
         */
        private boolean hasFlash() {
            return getApplicationContext().getPackageManager()
                    .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        }

        public void switchFlashlight(View view) {
            if (chkflashonoff == false)
            {
                barcodeScannerView.setTorchOn();
                chkflashonoff = true;
            }

            else {
                barcodeScannerView.setTorchOff();
                chkflashonoff = false;
            }
        }


        @Override
        public void onTorchOn() {
            switchFlashlightButton.setImageResource(R.drawable.ic_flashoffwhite);
        }

        @Override
        public void onTorchOff() {
            switchFlashlightButton.setImageResource(R.drawable.ic_flashonwhite);
        }
}
