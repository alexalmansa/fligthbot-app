package com.example.fligthbot;

import android.os.Bundle;

import com.example.fligthbot.model.FlightBot;
import com.example.fligthbot.model.Message;
import com.example.fligthbot.model.MessageListAdapter;

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
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

        private RecyclerView mMessageRecycler;
        private MessageListAdapter mMessageAdapter;
        private EditText mChatBox;
        private Button mSendButton;
        private List<Message> messageList;
        private TextView tvWritting;
        FlightBot bot;

        @Override

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            bot = new FlightBot();

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
                    final Message resp = new Message(bot.getResponse(mChatBox.getText().toString().toLowerCase()),2);
                    messageList.add(mes);
                    mChatBox.setText("");
                    mMessageAdapter.notifyDataSetChanged();
                    tvWritting.setVisibility(View.VISIBLE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            messageList.add(resp);
                            mMessageAdapter.notifyDataSetChanged();
                            tvWritting.setVisibility(View.INVISIBLE);

                        }
                    }, 2000);


                }
            });


        }

        // cal modificar coses aqui

}


