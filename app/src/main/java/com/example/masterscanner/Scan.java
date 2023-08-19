package com.example.masterscanner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Scan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ScanOptions options=new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher=registerForActivityResult(new ScanContract(), result->
    {
       if(result.getContents() != null){
           AlertDialog.Builder builder=new AlertDialog.Builder(Scan.this);
           builder.setTitle("Result");
           builder.setMessage(result.getContents());
           builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                   finish();
               }
           }).show();
       }
    });
}