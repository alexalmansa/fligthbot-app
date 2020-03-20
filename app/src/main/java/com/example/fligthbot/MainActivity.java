package com.example.fligthbot;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

        private RecyclerView mMessageRecycler;
        private EditText mChatBox;
        private Button mSendButton;
        private List<Message> messageList;
        private TextView tvWritting;
        private MessageListAdapter mMessageAdapter;


        @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            
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


            mMessageAdapter = new MessageListAdapter(this, messageList);
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
                    //TODO: REND MESSAGE

                    Message mes = new Message(mChatBox.getText().toString(),1);

                    messageList.add(mes);
                    mMessageRecycler.scrollToPosition(messageList.size() - 1);


                    Unirest.setTimeouts(0, 0);
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try  {
                                try {
                                    HttpResponse<String> response = Unirest.post("http://62.57.180.90:4444")
                                            .header("Content-Type", "text/plain")
                                            .body("{\n\t\"User\":\"Marc\",\n\t\"Message\":\""+mChatBox.getText().toString() +"\"\n}")
                                            .asString();
                                    Response data = new Gson().fromJson(response.getBody(), Response.class);
                                    final Message resp = new Message(data.getFulfillmentText(),2);
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


