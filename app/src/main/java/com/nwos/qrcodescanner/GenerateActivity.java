package com.nwos.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.nwos.qrcodescanner.databinding.ActivityGenerateBinding;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateActivity extends AppCompatActivity {
    private ActivityGenerateBinding generateBinding;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generateBinding = ActivityGenerateBinding.inflate(getLayoutInflater());
        setContentView(generateBinding.getRoot());

        generateBinding.btnGenerateCode.setOnClickListener(view->{
            String data = generateBinding.edtData.getText().toString();

            if (data.isEmpty()){
                Toast.makeText(GenerateActivity.this, "Please enter some data to generate QR code", Toast.LENGTH_SHORT).show();
            }else {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.x;
                int dimen = width<height ? width:height;

                dimen = dimen * 3/4;

                qrgEncoder = new QRGEncoder(data,null, QRGContents.Type.TEXT,dimen);

                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    generateBinding.tvGenerateQR.setVisibility(View.GONE);
                    generateBinding.ivQRCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}