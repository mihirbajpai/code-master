package com.example.masterscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class ShowCodeActivity extends AppCompatActivity {
    ImageView codeImage;
    ImageButton share, save;
    private static final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

        Intent intent = getIntent();
        String data = intent.getStringExtra("d");
        int type = intent.getIntExtra("t", 5);
        codeImage=findViewById(R.id.codeImage);

        //For qr code
        if(type==0){
            MultiFormatWriter multiFormatWriter=new MultiFormatWriter();

            try {
                BitMatrix bitMatrix=multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                codeImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        for bar code
        if(type==1){
            MultiFormatWriter multiFormatWriter=new MultiFormatWriter();

            try {
                BitMatrix bitMatrix=multiFormatWriter.encode(data, BarcodeFormat.CODE_128, 500, 200);
                BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
                Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
                codeImage.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        share=findViewById(R.id.share);
        save=findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(ShowCodeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    saveToGallery();
                }else {
                    ActivityCompat.requestPermissions(ShowCodeActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQr();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                saveToGallery();
            }else {
                Toast.makeText(ShowCodeActivity.this, "Please provide required permission",
                        Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void saveToGallery(){
        Uri images;
        ContentResolver contentResolver=getContentResolver();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            images= MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        }else {
            images=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentValues contentValues=new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis()+".jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "images/*");
        Uri uri=contentResolver.insert(images, contentValues);

        try{
            BitmapDrawable bitmapDrawable= (BitmapDrawable) codeImage.getDrawable();
            Bitmap bitmap=bitmapDrawable.getBitmap();

            OutputStream outputStream=contentResolver.openOutputStream(Objects.requireNonNull(uri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);

            Toast.makeText(ShowCodeActivity.this, "Image saved to gallery",
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(ShowCodeActivity.this, "Image not saved",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void shareQr(){

        BitmapDrawable drawable= (BitmapDrawable) codeImage.getDrawable();
        Bitmap bitmap=drawable.getBitmap();

        Uri uri=getImagesToShare(bitmap);

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share image"));

    }

    private Uri getImagesToShare(Bitmap bitmap){
        File folder=new File(getCacheDir(), "images");
        Uri uri=null;

        try{
            folder.mkdirs();
            File file=new File(folder, "shared_image.jpg");
            FileOutputStream fileOutputStream=null;

            fileOutputStream =new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            uri= FileProvider.getUriForFile(this, "com.example.masterscanner", file);
        }catch (Exception e){
            Toast.makeText(ShowCodeActivity.this, e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return uri;
    }
}

