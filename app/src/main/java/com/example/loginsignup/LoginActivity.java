package com.example.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity{


    DatabaseHelper db;
    Button login_btn, register_btn;
    EditText phone_edt, passowrd_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        phone_edt = findViewById(R.id.phoneNum_edt);
        passowrd_edt = findViewById(R.id.password_edt);
        login_btn = findViewById(R.id.login_button);
        register_btn = findViewById(R.id.register_button);

        db = new DatabaseHelper(this);
//        db.getWritableDatabase();



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int authentication;
                String phoneString;
                String password;

                phoneString = phone_edt.getText().toString().trim();
                password = passowrd_edt.getText().toString().trim();

                if(phoneString.isEmpty()|| password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG).show();
                }else{
                    int phoneNum = Integer.parseInt(phoneString);
                    authentication = db.authentication(phoneNum, password);
                    if(authentication == -1){
                        Toast.makeText(getApplicationContext(), "Wrong Phone Number or Password", Toast.LENGTH_LONG).show();
                    }else{
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("hostId", authentication);
                        startActivity(intent);
                    }

                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}