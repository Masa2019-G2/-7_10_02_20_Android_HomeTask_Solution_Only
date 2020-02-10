package com.example.a10_02_20;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoreProvider provider = new StoreProvider(this);

        if(provider.getToken() != null){
            showNextView();
        }
        setContentView(R.layout.activity_main);

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener( v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            provider.saveToken(email,password);
            showNextView();
        });
    }


    public void showNextView(){
        Intent intent = new Intent(this,ListActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode != RESULT_OK){
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
