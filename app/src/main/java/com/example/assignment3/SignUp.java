package com.example.assignment3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SignUp extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

   private String fnameTemp;
   private String lnameTemp;
   private String genderTemp;
   String dobTemp;
   private TextView dob;
   private String addressTemp;
   private String stateSpinnerTemp;
   private String postcodeTemp;
   private String  emailTemp;
   private String passwordTempHash;
   private EditText fname;
   private  EditText lname;
   private RadioGroup rggender;
   private RadioButton rbgender;
   private EditText address;
   private EditText postcode;
   private EditText email;
   private EditText password;
   private TextView login;
   Spinner state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dob = findViewById(R.id.tv_dob);;
        Button register = findViewById(R.id.add_button_register);

        /*Action performed on click of registeration button*/
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                fname = findViewById(R.id.et_fname);
                lname = findViewById(R.id.et_lname);
                rggender= findViewById(R.id.radio_grp);
                int genderseleted = rggender.getCheckedRadioButtonId();
                rbgender = findViewById(genderseleted);
                address = findViewById(R.id.et_Address);
                postcode = findViewById(R.id.et_postcode);
                email = findViewById(R.id.et_su_email);
                password = findViewById(R.id.et_su_password);
                fnameTemp = fname.getText().toString();
                lnameTemp = lname.getText().toString();
                genderTemp = rbgender.getText().toString();
                addressTemp = address.getText().toString();
                postcodeTemp = postcode.getText().toString();
                emailTemp = email.getText().toString();
               // CheckEmailExistAsyncTask emialcheck = new CheckEmailExistAsyncTask();
                //emialcheck.execute(emailTemp);
                String passwordTemp = password.getText().toString();
                passwordTempHash = Credentials.HashConverterPassword(passwordTemp) ;
                /*Fetching Current date*/
                String joinDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
                /*Async task will update the Users table and Credential table*/
                UpdateDataAsyncTask updateAsyncTask=new UpdateDataAsyncTask();
                if (!(fnameTemp).isEmpty() && !(lnameTemp).isEmpty() && !(genderTemp).isEmpty() && !(dobTemp).isEmpty()  && !(addressTemp).isEmpty() && !(postcodeTemp).isEmpty() && !(stateSpinnerTemp).isEmpty() && !(emailTemp).isEmpty() && !(passwordTempHash).isEmpty()) {

                    updateAsyncTask.execute(fnameTemp,lnameTemp,genderTemp,dobTemp,addressTemp,stateSpinnerTemp,postcodeTemp,emailTemp,passwordTempHash,joinDate);
                }
            }


        });

      /*click to set date of birth which will call date picker*/
        findViewById(R.id.btn_dob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

     /*going back to main activity on click of login button*/
        login = findViewById(R.id.re_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
            }
        });

     /*Spinner code for selection of states*/
        state = findViewById(R.id.sp_states);
        List<String> list = new ArrayList<>();
        list.add("Choose State");
        list.add("new south wales");
        list.add("queensland");
        list.add("south australia");
        list.add("tasmania");
        list.add("victoria");
        list.add("western australia");

      /* code to set the spinner list to adapter*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        state.setAdapter(adapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateSpinnerTemp = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }

     /*Async task for the update of data to users and credentail table at server side*/
     class UpdateDataAsyncTask extends AsyncTask<String, Void, String>
     {
        @Override
        protected String doInBackground(String... params) {
            String id = RestClient.getMaxId("users");
            Integer uId = Integer.parseInt(id) + 1;

            /*Code to check that for username in credential if it already exist don't add user or credential*/
            String getUserCredential = RestClient.getUserCredentials(params[7]);
            JSONArray jsonArray = null;
            try{

                jsonArray = new JSONArray(getUserCredential);
                // JSONObject object = jsonArray.getJSONObject(0);
                if(jsonArray.length() == 0)
                {
                    Users user=new Users(params[4],Date.valueOf(params[3]),params[2],uId,params[0],Integer.valueOf(params[6]),params[5] ,params[1]);
                    RestClient.postUsers(user);
                    Credentials usercredential = new Credentials(params[8],Date.valueOf(params[3]),params[7],user);
                    RestClient.postCredential(usercredential);
                    return "User was added!";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
               return "User cannot be added !";
        }
        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignUp.this,MainActivity.class);
            startActivity(intent);
        }
     }


      /*Date picker code*/
        public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR),  Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        dobTemp = year+"-"+month+"-"+dayOfMonth;
        dob = findViewById(R.id.tv_dob);
        dob.setText(dobTemp);
    }


}
