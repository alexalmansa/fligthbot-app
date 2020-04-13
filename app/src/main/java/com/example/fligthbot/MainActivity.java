package com.example.fligthbot;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import com.example.fligthbot.model.Message;
import com.example.fligthbot.model.MessageListAdapter;
import com.example.fligthbot.model.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private static String IP ;
        private static String NAME;

        private final static String PORT = "4444";


        private static SharedPreferences sharedPreferences;

        private RecyclerView mMessageRecycler;
        private EditText mChatBox;
        private Button mSendButton;
        private ImageView ivSettings;


        private List<Message> messageList;
        private TextView tvWritting;
        private MessageListAdapter mMessageAdapter;
        private DateFormat outputformat;
        static HttpListener listener;

        public interface HttpListener {
            // you can define any parameter as per your requirement
            public void callback(boolean success);
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            outputformat = new SimpleDateFormat("HH:mm");

            prepareListener();
            checkPreferences();

            viewSetup();
            chatBoxSetup();

        }

        private void checkPreferences(){
            messageList = new ArrayList<>();
            sharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFERENCES), Context.MODE_PRIVATE);
            IP = sharedPreferences.getString(getString(R.string.IP_KEY), null);
            if (IP == null){
                Intent intent = new Intent(this, ConfigActivity.class);
                startActivity(intent);
                IP = sharedPreferences.getString(getString(R.string.IP_KEY), null);
            }
            NAME = sharedPreferences.getString(getString(R.string.NAME_KEY), null);

            String jsonPreferences = sharedPreferences.getString(getString(R.string.MESSAGE_KEY), "");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Message>>() {}.getType();
            if (gson.fromJson(jsonPreferences, type) != null){
                messageList = gson.fromJson(jsonPreferences, type);
            }
        }

        private void prepareListener(){
            listener = new HttpListener() {
                @Override
                public void callback(boolean success) {
                    if (success) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvWritting.setVisibility(View.INVISIBLE);
                                mMessageAdapter.notifyDataSetChanged();
                                mMessageRecycler.scrollToPosition(messageList.size() - 1);
                            }
                        });

                    }else {

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvWritting.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this,
                                        "Error connecting to GoFly server, check that you have the correct ip and that the server is running", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    }

            };
        }

        private void viewSetup() {

            tvWritting = findViewById(R.id.tv_writting);
            mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
            mMessageRecycler.setNestedScrollingEnabled(false);
            mMessageRecycler.setHasFixedSize(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

            linearLayoutManager.setStackFromEnd(true);

            mMessageRecycler.setLayoutManager(linearLayoutManager);


            mMessageAdapter = new MessageListAdapter(getBaseContext(), messageList);
            mMessageRecycler.setAdapter(mMessageAdapter);

            ivSettings = findViewById(R.id.iv_settings);
            ivSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ConfigActivity.class);
                    startActivity(intent);
                    IP = sharedPreferences.getString(getString(R.string.IP_KEY), null);
                    NAME = sharedPreferences.getString(getString(R.string.NAME_KEY), null);
                    String jsonPreferences = sharedPreferences.getString(getString(R.string.MESSAGE_KEY), "");
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Message>>() {}.getType();
                    ArrayList<Message> messages = gson.fromJson(jsonPreferences, type);
                    if (messages == null){
                        messageList.clear();
                    }
                    mMessageAdapter.notifyDataSetChanged();
                }
            });

        }

        private void chatBoxSetup() {

            mChatBox = (EditText) findViewById(R.id.edittext_chatbox);
            mSendButton = (Button) findViewById(R.id.button_chatbox_send);

            mChatBox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (charSequence.toString().trim().length() > 0) {
                        mSendButton.setEnabled(true);
                    } else {
                        mSendButton.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            onClickSend();

        }

        private void onClickSend(){
            // Send text button
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Message mes = new Message(mChatBox.getText().toString(),Message.VIEW_TYPE_MESSAGE_SENT,outputformat.format(new Date()) );

                    messageList.add(mes);
                    mMessageRecycler.scrollToPosition(messageList.size() - 1);
                    httpRequest();
                    mChatBox.setText("");
                    mMessageAdapter.notifyDataSetChanged();
                    tvWritting.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {




                        }
                    }, 2500);
                }
            });
        }
        private void httpRequest(){
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        try {
                            String mess = mChatBox.getText().toString().replaceAll("\n", "");
                            Unirest.setTimeouts(0, 0);
                            HttpResponse<String> response = Unirest.post("http://" +IP + ":"+PORT)
                                    .header("Content-Type", "text/plain")
                                    .body("{\r\n\t\"User\":\"" + NAME + "\",\r\n\t\"Message\":\"" + mess + "\"\r\n}")
                                    .asString();
                            Response data = new Gson().fromJson(response.getBody(), Response.class);
                            listener.callback(true);
                            Message mes;
                            if (data.getIntent().equals("arribe")){
                                mes  = new Message(data.getFulfillmentText(),Message.VIEW_TYPE_MESSAGE_RECEIVED, "google.navigation:q="+data.getFulfillmentText() ,outputformat.format(new Date()));

                            }else if (data.getIntent().equals("address")){
                                mes = new Message(data.getFulfillmentText(),Message.VIEW_TYPE_MESSAGE_RECEIVED, "geo:0,0?q="+data.getFulfillmentText() ,outputformat.format(new Date()));

                            } else if (data.getIntent().equals("Info") || data.getIntent().equals("travel to city - yes")){
                                String address = data.getFulfillmentText().substring(data.getFulfillmentText().indexOf(":")+1);
                                address = address.substring(0,address.indexOf("--"));
                                mes = new Message(data.getFulfillmentText(),Message.VIEW_TYPE_MESSAGE_RECEIVED, "geo:0,0?q="+address ,outputformat.format(new Date()));

                            }else{
                                mes = new Message(data.getFulfillmentText(),Message.VIEW_TYPE_MESSAGE_RECEIVED,outputformat.format(new Date()));

                            }
                            messageList.add(mes);

                        } catch (UnirestException e) {
                            e.printStackTrace();
                            listener.callback(false);

                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        listener.callback(false);
                    }
                }
            });

            thread.start();

        }

        @Override
        protected void onPause() {
            Gson gson = new Gson();
            String jsonCurProduct = gson.toJson(messageList);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(getString(R.string.MESSAGE_KEY), jsonCurProduct);
            editor.commit();
            super.onPause();
        }
}


