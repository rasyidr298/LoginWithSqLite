package com.rrdev.loginwithsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sqliteHelper = new SqliteHelper(this);
        initViews();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {
        editTextUsername = (EditText) findViewById(R.id.edit_username);
        editTextPassword = (EditText) findViewById(R.id.edit_password);

    }

    //This method is used to validate input given by user
    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (username.isEmpty()) {
            valid = false;
            editTextUsername.setError("Please enter valid username!");
        } else {
            if (username.length() > 5) {
                valid = true;
                editTextUsername.setError(null);
            } else {
                valid = false;
                editTextUsername.setError("Username is to short!");
            }
        }

        //Handling validation for Password field
        if (password.isEmpty()) {
            valid = false;
            editTextPassword.setError("Please enter valid password!");
        } else {
            if (password.length() > 5) {
                valid = true;
                editTextPassword.setError(null);
            } else {
                valid = false;
                editTextPassword.setError("Password is to short!");
            }
        }


        return valid;
    }

    public void registerClick(View view){
        if (validate()) {
            String user = editTextUsername.getText().toString();
            String pass = editTextPassword.getText().toString();

            //Check in the database is there any user associated with  this username
            if (!sqliteHelper.isUserExists(user)) {

                //Email does not exist now add new user to database
                sqliteHelper.addUser(new User(null, user, pass));
                Toast.makeText(RegisterActivity.this, "User created successfully! Please Login ", Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, Toast.LENGTH_LONG);
            }else {

                //Email exists with email input provided so show error user already exist
                Toast.makeText(RegisterActivity.this, "User already exists with same email ", Toast.LENGTH_LONG).show();
            }


        }
    }

}
