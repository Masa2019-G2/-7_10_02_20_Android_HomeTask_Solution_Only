package com.example.a10_02_20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    //    ProgressBar myProgress;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button loginBtn;
    private StoreProvider provider;
    private FrameLayout progressFrame;
    private AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        provider = new StoreProvider(this);

//        if (provider.getToken() != null) {
//            showNextView();
//        }
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_second);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        loginBtn = findViewById(R.id.loginBtn);
//        myProgress = findViewById(R.id.myProgress);
        progressFrame = findViewById(R.id.progressFrame);
        progressFrame.setOnClickListener(null);

        loginBtn.setOnClickListener(v -> {
            new LoginTask().execute();
//            String email = inputEmail.getText().toString();
//            String password = inputPassword.getText().toString();
//            provider.saveToken(email, password);
//            showNextView();
        });

//        new ValidationTask().execute();
        Handler handler = new Handler(msg -> {
            switch (msg.what) {
                case ValidationWorker.VALIDATION_START:
                    showProgress(true);
                    return true;
                case ValidationWorker.VALIDATION_SUCCESS:
                    showProgress(false);
                    showNextView();
                    return true;
                case ValidationWorker.VALIDATION_FAILURE:
                    showProgress(false);
                    return true;
            }
            return false;
        });
        new Thread(new ValidationWorker(handler, provider)).start();
    }


    public void showNextView() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode != RESULT_OK) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showProgress(boolean isShow) {
//        loginBtn.setEnabled(!isShow);
//        inputEmail.setEnabled(!isShow);
//        inputPassword.setEnabled(!isShow);
//        myProgress.setVisibility(isShow ? View.VISIBLE : View.GONE);
//        progressFrame.setVisibility(isShow ? View.VISIBLE : View.GONE);
        if (isShow) {
            progressDialog = new AlertDialog.Builder(this)
                    .setView(R.layout.dialog_progress)
                    .setCancelable(false)
                    .create();
            progressDialog.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    class ValidationTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (provider.getToken() != null) {
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            showProgress(false);
            if (result) {
                showNextView();
            }
        }
    }


    class LoginTask extends AsyncTask<Void, Void, Boolean> {
        String email, password;

        @Override
        protected void onPreExecute() {
            showProgress(true);
            email = inputEmail.getText().toString();
            password = inputPassword.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return provider.saveToken(email, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            showProgress(false);
            if (result) {
                showNextView();
            }
        }
    }


}
