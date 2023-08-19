package com.example.masterscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int selectTab=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout qrLayout=findViewById(R.id.qrLayout);
        final LinearLayout scanLayout=findViewById(R.id.scanLayout);
        final LinearLayout barLayout=findViewById(R.id.barLayout);

        final ImageView qrImage=findViewById(R.id.qrImage);
        final ImageView scanImage=findViewById(R.id.scanImage);
        final ImageView barImage=findViewById(R.id.barImage);

        final TextView qrText=findViewById(R.id.qrText);
        final TextView scanText=findViewById(R.id.scanText);
        final TextView barText=findViewById(R.id.barText);

        getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainer, QrFragment.class, null)
                        .commit();

        qrLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectTab!=1){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, QrFragment.class, null)
                            .commit();

                    scanText.setVisibility(View.GONE);
                    barText.setVisibility(View.GONE);

                    scanImage.setImageResource(R.drawable.scan);
                    barImage.setImageResource(R.drawable.barcode);

                    scanLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    barLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    qrText.setVisibility(View.VISIBLE);
                    qrImage.setImageResource(R.drawable.qrcode_select);
                    qrLayout.setBackgroundResource(R.drawable.round_background_qr_20);

                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    qrLayout.startAnimation(scaleAnimation);
                    selectTab=1;
                }
            }
        });

        scanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectTab!=2){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, ScanFragment.class, null)
                            .commit();

                    qrText.setVisibility(View.GONE);
                    barText.setVisibility(View.GONE);

                    qrImage.setImageResource(R.drawable.qrcode);
                    barImage.setImageResource(R.drawable.barcode);

                    qrLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    barLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    scanText.setVisibility(View.VISIBLE);
                    scanImage.setImageResource(R.drawable.scan_select);
                    scanLayout.setBackgroundResource(R.drawable.round_background_scan_20);

                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    scanLayout.startAnimation(scaleAnimation);
                    selectTab=2;
                }
            }
        });

        barLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectTab!=3){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, BarFragment.class, null)
                            .commit();
                    qrText.setVisibility(View.GONE);
                    scanText.setVisibility(View.GONE);

                    qrImage.setImageResource(R.drawable.qrcode);
                    scanImage.setImageResource(R.drawable.scan);

                    qrLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    scanLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    barText.setVisibility(View.VISIBLE);
                    barImage.setImageResource(R.drawable.barcode_select);
                    barLayout.setBackgroundResource(R.drawable.round_background_bar_20);

                    ScaleAnimation scaleAnimation=new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    barLayout.startAnimation(scaleAnimation);
                    selectTab=3;
                }
            }
        });
    }
}




