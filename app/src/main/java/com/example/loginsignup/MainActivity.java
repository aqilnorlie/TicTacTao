package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText nickname_edt;
    Button insert_nickname_btn, start_game_btn;
    Spinner player1, player2;
    int hostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        hostId = intent.getIntExtra("hostId", -1);

        nickname_edt = findViewById(R.id.nickname_edt);
        insert_nickname_btn = findViewById(R.id.insert_nickname_btn);
        start_game_btn = findViewById(R.id.start_game_btn);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);

        db = new DatabaseHelper(this);

        // Initial load of spinner items
        refreshSpinners();

        insert_nickname_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickname;
                boolean ins;
                nickname = nickname_edt.getText().toString().trim();

                ins = db.insertNickname(hostId, nickname);
                if(ins){
                    Toast.makeText(getApplicationContext(), "Successfully add new nickname", Toast.LENGTH_LONG).show();
                    refreshSpinners();
                }else{
                    Toast.makeText(getApplicationContext(), "This nickname already insert, please try others", Toast.LENGTH_LONG).show();
                }


            }
        });

        start_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String player1Nickname = player1.getSelectedItem().toString();
                String player2Nickname = player2.getSelectedItem().toString();

                if(player1Nickname.equals("Select nickname") || player2Nickname.equals("Select nickname")){
                    Toast.makeText(getApplicationContext(), "Please Select Players Nickname", Toast.LENGTH_LONG).show();
                }

                if(player1Nickname.equals(player2Nickname)){
                    Toast.makeText(getApplicationContext(), "Please Select Different Nickname Players", Toast.LENGTH_LONG).show();
                }else{
                    //go to tictactao
                }





            }
        });



    }



    private void refreshSpinners() {
        // Fetch nicknames from database
        List<String> nicknames = db.getAllNicknames(hostId);

        List<String> spinnerItems = new ArrayList<>();
        spinnerItems.add("Select nickname");
        spinnerItems.addAll(nicknames);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        player1.setAdapter(adapter);
        player2.setAdapter(adapter);
    }

}