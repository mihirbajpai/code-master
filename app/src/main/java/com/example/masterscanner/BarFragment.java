package com.example.masterscanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BarFragment extends Fragment {
    private Button barGenerateButton;
    private EditText barGenerateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bar, container, false);

        barGenerateButton=view.findViewById(R.id.barGenerateButton);
        barGenerateText=view.findViewById(R.id.barGenerateText);
        barGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=barGenerateText.getText().toString();

                if (data.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter some data to generate QR Code.", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(getContext(), ShowCodeActivity.class);
                    intent.putExtra("d", data);
                    intent.putExtra("t", 1);
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}