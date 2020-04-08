package com.example.fligthbot;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import com.example.fligthbot.model.Message;
import com.example.fligthbot.model.MessageListAdapter;
import com.example.fligthbot.model.Response;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private final static String IP = "10.0.2.2";
        private final static String PORT = "4444";

        private RecyclerView mMessageRecycler;
        private EditText mChatBox;
        private Button mSendButton;
        private List<Message> messageList;
        private TextView tvWritting;
        private MessageListAdapter mMessageAdapter;
        private DateFormat outputformat;


        @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            outputformat = new SimpleDateFormat("HH:mm");
            viewSetup();
            chatBoxSetup();


        }


        private void viewSetup() {
            messageList = new ArrayList<>();

            tvWritting = findViewById(R.id.tv_writting);
            mMessageRecycler = (RecyclerView) findViewById(R.id.reyclerview_message_list);
            mMessageRecycler.setNestedScrollingEnabled(false);
            mMessageRecycler.setHasFixedSize(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

            linearLayoutManager.setStackFromEnd(true);

            mMessageRecycler.setLayoutManager(linearLayoutManager);


            mMessageAdapter = new MessageListAdapter(getBaseContext(), messageList);
            mMessageRecycler.setAdapter(mMessageAdapter);

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

            // Send text button
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Message mes = new Message(mChatBox.getText().toString(),Message.VIEW_TYPE_MESSAGE_SENT,outputformat.format(new Date()) );

                    messageList.add(mes);
                    mMessageRecycler.scrollToPosition(messageList.size() - 1);

                    mes = new Message(mChatBox.getText().toString(),Message.VIEW_TYPE_MESSAGE_RECEIVED, "google.navigation:q=Passeig de Gràcia, 43, 08007 Barcelona, Spain",outputformat.format(new Date()));

                    messageList.add(mes);
                    mMessageRecycler.scrollToPosition(messageList.size() - 1);



                    //Open maps with navigation to go to this adress
                    //Uri gmmIntentUri = Uri.parse("google.navigation:q=Passeig de Gràcia, 43, 08007 Barcelona, Spain");

                    //Open maps with direction
                    //geo:0,0?q=Passeig de Gràcia, 43, 08007 Barcelona, Spain



                    Unirest.setTimeouts(0, 0);
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                try {
                                    HttpResponse<String> response = Unirest.post(IP + ":" + PORT )
                                            .header("Content-Type", "text/plain")
                                            .body("{\n\t\"User\":\"Alex\",\n\t\"Message\":\""+mChatBox.getText().toString() +"\"\n}")
                                            .asString();
                                    Response data = new Gson().fromJson(response.getBody(), Response.class);
                                    if (data.getIntent().equals("Info")){


                                    }
                                    Date time = new Time(System.currentTimeMillis());
                                    final Message resp = new Message(data.getFulfillmentText(),Message.VIEW_TYPE_MESSAGE_RECEIVED,outputformat.format(new Date()));

                                    messageList.add(resp);
                                } catch (UnirestException e) {
                                    e.printStackTrace();

                                }                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();





                    mChatBox.setText("");
                    mMessageAdapter.notifyDataSetChanged();
                    tvWritting.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            mMessageAdapter.notifyDataSetChanged();
                            tvWritting.setVisibility(View.INVISIBLE);
                            mMessageAdapter.notifyDataSetChanged();
                            mMessageRecycler.scrollToPosition(messageList.size() - 1);

                        }
                    }, 2000);


                }
            });


        }

        // cal modificar coses aqui

}


