package com.nwos.qrcodescanner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Toast;

import com.nwos.qrcodescanner.databinding.ActivityScanBinding;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanActivity extends AppCompatActivity {
    private ActivityScanBinding scanBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanBinding =ActivityScanBinding.inflate(getLayoutInflater());
        setContentView(scanBinding.getRoot());


        if (checkPermission()){
            Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
        }else {
            requestPermission();
        }

        scanBinding.camView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScanActivity.this, "Scanner started...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScanActivity.this, "Scanner stopped...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(ScanActivity.this, "Can't scanned because "+err.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                scanBinding.tvScannedData.setText(data);
            }
        });

        scanBinding.tvScannedData.setOnClickListener(view->{
            Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse(scanBinding.tvScannedData.getText().toString()));
            startActivity(browser);
        });
    }

   private boolean checkPermission(){
        int camer_permission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
       int vibrate_permission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);

       return  camer_permission == PackageManager.PERMISSION_GRANTED && vibrate_permission == PackageManager.PERMISSION_GRANTED;
   }
   private void requestPermission(){
        int PERMISSION_CODE =200;
       ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},PERMISSION_CODE);
   }

    @Override
    protected void onPause() {
        scanBinding.camView.stopScanner();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scanBinding.camView.setDecoder(decoder);
        scanBinding.camView.startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0){
            boolean camerAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (camerAccepted && vibrationAccepted){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission Denied \n You can't use the app without permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}