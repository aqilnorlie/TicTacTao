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

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText username_edt, password1_edt, password2_edt, phoneNum_edt;
    Button signUp_btn, back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        username_edt = findViewById(R.id.usernameRegister);
        phoneNum_edt = findViewById(R.id.phoneNumRegister);
        password1_edt = findViewById(R.id.password1);
        password2_edt = findViewById(R.id.password2);
        signUp_btn = findViewById(R.id.signup_button);
        back_btn = findViewById(R.id.back_button);

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password1, password2, phoneString;
                boolean checkExist, ins;

                username = username_edt.getText().toString().trim();
                phoneString = phoneNum_edt.getText().toString().trim();
                password1 = password1_edt.getText().toString().trim();
                password2 = password2_edt.getText().toString().trim();

                if(username.isEmpty() || password1.isEmpty() || password2.isEmpty()|| phoneString.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_LONG).show();
                }else{
                    int phoneNum = Integer.parseInt(phoneString);
                    checkExist = db.checkPhoneNumExist(phoneNum);
                    if(!checkExist){
                        Toast.makeText(getApplicationContext(), "This number phone already register, Continue Login", Toast.LENGTH_LONG).show();
                    }else{
                        if(password1.equals(password2)){
                            ins = db.register(username, password1, phoneNum);
                            if(ins){
                                Toast.makeText(getApplicationContext(), "Succesfully Register", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Password Do not Match", Toast.LENGTH_LONG).show();
                        }
                    }
                }


            }
        });






    }
}