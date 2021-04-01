package com.example.smsapppermission;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText number, message;
    private Button send;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.number);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    sendMessages();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
                }
            }
        });
    }

    private void sendMessages() {

        String NUMBER = number.getText().toString().trim();
        String MESSAGE = message.getText().toString().trim();

        if (!NUMBER.equals("") && !MESSAGE.equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(NUMBER, null, MESSAGE, null, null);
            Toast.makeText(getApplicationContext(), "SMS Sent", Toast.LENGTH_LONG).show();
            number.setText("");
            message.setText("");

        } else {
            Toast.makeText(this, "Value Can't be empty", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessages();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have sms permission...", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}