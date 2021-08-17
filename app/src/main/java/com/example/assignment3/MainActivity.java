package com.example.assignment3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String usernameString;
    String passwordString;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.add_button_login);
        TextView signup = findViewById(R.id.tv_signup);
        /*OnClick operation on loogin button*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = findViewById(R.id.et_userid);
                password = findViewById(R.id.et_password);
                usernameString = username.getText().toString();
                passwordString = password.getText().toString();
                /* Calling Async task to check the credentials and password from server side database*/
                CredentialAsyncTask getCredential = new CredentialAsyncTask();
                getCredential.execute(usernameString);
            }
        });
        /*OnClick operation on Signup button*/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    /*Async Task method to check for user id and password */
    private class CredentialAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.getUserCredentials(params[0]);
        }
        @Override
        protected void onPostExecute(String json) {
            if (json!= null && json.length()!=2)
            {
                String password = Credentials.HashConverterPassword(passwordString);
                String sub = json.substring(1, json.length() -1);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Credentials credentialUser = gson.fromJson(sub, Credentials.class);
                Users user = credentialUser.getUser();
                if (password.equals(credentialUser.getPassword())) {
                    /*Passing Users object to the home screen*/
                    Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user",user);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), "Your Password is incorrect", Toast.LENGTH_LONG).show();

                }

            }else{
                Toast.makeText(getApplicationContext(), "Your Id or Password is incorrect", Toast.LENGTH_LONG).show();
            }

        }

    }
}

