package com.example.masterscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.StrictMode;
import android.os.storage.StorageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrFragment extends Fragment {
    private Button qrGenerateButton;
    private EditText qrGenerateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_qr, container, false);

        qrGenerateButton=view.findViewById(R.id.qrGenerateButton);
        qrGenerateText=view.findViewById(R.id.qrGenerateText);
        qrGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=qrGenerateText.getText().toString();

                if (data.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter some data to generate QR Code.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(getContext(), ShowCodeActivity.class);
                    intent.putExtra("d", data);
                    intent.putExtra("t", 0);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}