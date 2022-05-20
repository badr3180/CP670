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

    public void login(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        String file_name = getString(R.string.preference_name);
        SharedPreferences myPrefs = getSharedPreferences(file_name, MODE_PRIVATE);
        String email_key  = getString(R.string.key_email);
        SharedPreferences.Editor myEditor = myPrefs.edit();
        myEditor.clear();
        String new_email_entered = ((EditText) findViewById(R.id.editTextTextPersonName))
                .getText().toString();
        myEditor.putString(email_key, new_email_entered);
        myEditor.commit();
        Log.i(email_key, new_email_entered);
        ((EditText) findViewById(R.id.editTextTextPersonName)).setText(new_email_entered);
        startActivity(intent);
    }
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
        ((EditText) findViewById(R.id.editTextTextPersonName)).setText(new_email_value);
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