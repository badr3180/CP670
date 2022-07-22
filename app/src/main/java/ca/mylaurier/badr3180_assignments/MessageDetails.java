package ca.mylaurier.badr3180_assignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("Message", "NoMessage");
        long ID = bundle.getLong("_id", 00);
        Log.i("bundle ID", String.valueOf(ID));
        Log.i("Bundle String", message);

        MessageFragment messageFragment = new MessageFragment().newInstance(0,"",null, null, null, null);
        messageFragment.setArguments(bundle);   // (1) Communicate with Fragment using Bundle
        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, messageFragment);// begin  FragmentTransaction
        //ft.add(R.id.frameContainer, messageFragment);     // add    Fragment
        ft.commit();
        //finish();
    }
}