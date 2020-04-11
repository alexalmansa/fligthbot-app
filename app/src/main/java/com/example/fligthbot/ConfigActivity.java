package com.example.fligthbot;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ConfigActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextInputEditText tiIp;
    TextInputEditText tiName;
    Button btnSave;
    Button btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_activity);
        configureView();
    }

    private void configureView(){
        tiIp = findViewById(R.id.ti_ip);
        tiName = findViewById(R.id.ti_name);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString(getString(R.string.IP_KEY), null);
        String name = sharedPreferences.getString(getString(R.string.NAME_KEY), null);
        if (name != null && ip != null){
            tiIp.setText(ip);
            tiName.setText(name);
            btnDelete.setVisibility(View.VISIBLE);

        }
        tiIp = findViewById(R.id.ti_ip);
        tiName = findViewById(R.id.ti_name);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                boolean error = false;
                if (validateIP(tiIp.getText().toString())){

                }else {
                    error = true;
                    tiIp.setError("Invalid IP");
                }
                if (tiName.getText().toString() == null || tiName.getText().toString().length() < 1){
                    tiName.setError("Field name can't be empty!");
                    error = true;
                }
                if (!error){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.IP_KEY), tiIp.getText().toString());
                    editor.putString(getString(R.string.NAME_KEY), tiName.getText().toString());
                    editor.apply();
                    finish();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(ConfigActivity.this);
                adb.setTitle("Are you sure you want to delete the chat histoty?");
                adb.setIcon(R.drawable.ic_warning);

                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMesages();
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                adb.show();
            }
        });
    }

    private void deleteMesages(){
        ArrayList<Message> m = new ArrayList<>();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.MESSAGE_KEY), m.toString());
        editor.commit();
    }

    private static boolean validateIP(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }
}
