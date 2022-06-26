package ca.mylaurier.badr3180_assignments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.mylaurier.badr3180_assignments.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {
    AppCompatActivity mainActivity;
    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    Snackbar snackbar;
    String message = "";
    private nullFieldValidator fieldValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

      /*  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.floating_snack), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

  /*  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem mi){
        switch (mi.getItemId()) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                if (message == "") {
                    snackbar.make(this.findViewById(R.id.testToolbar), getString(R.string.option1snack), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    snackbar.make(this.findViewById(R.id.testToolbar), message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;

            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                Option2();
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                Option3();
                break;
            case R.id.settings:
                Log.d("Toolbar", "About selected");
                String toastMsg = getString(R.string.about_toast);
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(TestToolbar.this , toastMsg, duration); //this is the ListActivity
                toast.show(); //display your message box
                break;
            default:
                Log.d("Default", "Default selected");
        }

        return true;
    }

    public void Option2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.custom_dialog_title);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void Option3() {
        AlertDialog.Builder customDialog = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.custom_dialog, null);
        customDialog.setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText edit = view.findViewById(R.id.dialog_message_box);
                        fieldValidator = new nullFieldValidator();
                        edit.addTextChangedListener(fieldValidator);
                        Log.i("testvalidation",edit.getText().toString());
                        message = edit.getText().toString();
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        Dialog dialog = customDialog.create();
        dialog.show();
    }
}