package ca.mylaurier.badr3180_assignments;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        ActivityResultLauncher<Intent> mStartForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                Bundle extras = result.getData().getExtras();
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                ImageButton btnImg = findViewById(R.id.imageButton);
                                btnImg.setImageBitmap(imageBitmap);
                            }
                        });
        ImageButton imgBtn = findViewById(R.id.imageButton);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The launcher with the Intent you want to start
                mStartForResult.launch(takePictureIntent);
            }
        });

        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String text = getString(R.string.switch_on);
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                    Toast toast = Toast.makeText(ListItemsActivity.this , text, duration); //this is the ListActivity
                    toast.show(); //display your message box
                } else {
                    String text = getString(R.string.switch_Off);
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(ListItemsActivity.this , text, duration); //this is the ListActivity
                    toast.show(); //display your message box
                }
            }
        });

        CheckBox chk = (CheckBox) findViewById(R.id.checkBox);
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                // Check which checkbox was clicked
                if (checked){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialog_message);
                    builder.setTitle(R.string.dialog_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent resultIntent = new Intent(  );
                            String response = getString(R.string.response);
                            resultIntent.putExtra("Response", response);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        }
                    })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .show();
                }
                else{
                    // Do NOTHING
                }
            }
        });
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