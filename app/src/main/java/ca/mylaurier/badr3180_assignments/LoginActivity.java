package ca.mylaurier.badr3180_assignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    // The validator for the email input field.
    private EmailValidator mEmailValidator;
    private EditText mEmailText;
    private EditText passWordText;
    private String currentEmail;
    private String passwordValue;
    private nullFieldValidator pwdValidator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);
        String email_key  = getString(R.string.key_email);
        String new_email_value = myPrefs.getString(email_key, "email@domain.com");
        Log.i("DefaultPreferenceValue", new_email_value );
        mEmailText = ((EditText) findViewById(R.id.editTextTextPersonName));
        mEmailText.setText(new_email_value);
        passWordText = ((EditText) findViewById(R.id.editTextTextPassword));
        // Setup field validators.
        //
        mEmailValidator = new EmailValidator();
        pwdValidator= new nullFieldValidator();
        mEmailText.addTextChangedListener(mEmailValidator);
        passWordText.addTextChangedListener(pwdValidator);
    }
    public void login(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);
        String email_key  = getString(R.string.key_email);
        SharedPreferences.Editor myEditor = myPrefs.edit();
        currentEmail = myPrefs.getString("EmailKey","");
        passwordValue = passWordText.getText().toString();
        Log.i("PasswordValue", passwordValue);
        myEditor.clear();
        String new_email_entered = mEmailText.getText().toString();
        if(new_email_entered.equals(currentEmail) || new_email_entered.equals("email@domain.com")){
            if(!pwdValidator.isValid()){
                passWordText.setError(getString(R.string.invalid_pwd));
                Log.w(ACTIVITY_NAME, "Not saving personal information: Null password");
                return;
            }
            else{
                myEditor.putString(email_key, new_email_entered);
                myEditor.commit();
                Log.i(email_key, new_email_entered);
                mEmailText.setText(new_email_entered);
                startActivity(intent);
            }
        }
        else{
            if (!mEmailValidator.isValid()) {
                mEmailText.setError(getString(R.string.ivalid_email));
                Log.w(ACTIVITY_NAME, "Not saving personal information: Invalid email");
                return;
            }
            if(!pwdValidator.isValid()){
                passWordText.setError(getString(R.string.invalid_pwd));
                Log.w(ACTIVITY_NAME, "Not saving personal information: Null password");
                return;
            }
            else{
                myEditor.putString(email_key, new_email_entered);
                myEditor.commit();
                Log.i(email_key, new_email_entered);
                mEmailText.setText(new_email_entered);
                startActivity(intent);
            }
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy");
    }
}