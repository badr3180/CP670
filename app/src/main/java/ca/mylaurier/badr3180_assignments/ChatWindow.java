package ca.mylaurier.badr3180_assignments;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
protected static final String ACTIVITY_NAME = "ChatActivity";
Button sendButton;
EditText chatText;
ListView listView;
public ArrayList<String> chatMSGS= new ArrayList<String>();
public nullFieldValidator nullFieldValidator;
public SQLiteDatabase database;
public ChatDatabaseHelper dbHelper;
public boolean isFramelayout = false;
    public Cursor cursor;
    public ChatAdapter messageAdapter;
    public MessageFragment messageFragment;
    public String messageText;
    public  Context context;
public class ChatAdapter extends ArrayAdapter<String>{
    public ChatAdapter(Context ctx) {
        super(ctx, 0);
        Log.d(ACTIVITY_NAME, " ChatAdapter comes to constructor");
    }

    @Override
    public int getCount() {
        Log.d(ACTIVITY_NAME, "ChatAdapter GetCount size: "+chatMSGS.size());
        return chatMSGS.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
            Log.d(ACTIVITY_NAME, " ChatMessageGetItem : "+chatMSGS.get(position));
        return String.valueOf(chatMSGS.get(position));
    }
    @Nullable
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
        //Log.d(ACTIVITY_NAME, "In chatAdaptergetView");
        View result =null;
        if(position%2 == 0)
            result = inflater.inflate(R.layout.chat_row_incoming, null);
        else
            result = inflater.inflate(R.layout.chat_row_outgoing, null);
        TextView messageView = result.findViewById(R.id.message_text);
        messageView.setText(getItem(position)); // get the string at position
        return result;
    }
    @Override
    public long getItemId(int position){
        long id=0;
        int colIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
        cursor.moveToPosition(position);
        Log.i("PositionCOunt", String.valueOf(cursor.getCount()));
        id = cursor.getLong(colIndex);
        return id;
    }
}

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        context = this.getBaseContext();
        Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
        ActivityResultLauncher<Intent> mStartForResult =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        new ActivityResultCallback<ActivityResult>() {
                            @Override
                            public void onActivityResult(ActivityResult result) {
                                if (result.getResultCode() == Activity.RESULT_OK) {
                                    Log.i(ACTIVITY_NAME, "Returned to ChatActivity with result");
                                    long ID = result.getData().getLongExtra("_id", 00);
                                    int position = result.getData().getIntExtra("Position",00);
                                    Log.i("RESULT FROM Delete message Fragment", String.valueOf(ID));
                                    Log.i("ITEM", String.valueOf(result.getData().getIntExtra("Position",00)));
                                    if(messageAdapter.getCount()>0){
                                        dbHelper.deleteItem(ID);
                                        chatMSGS.remove(position);
                                        //messageAdapter.remove(messageAdapter.getItem(position));
                                        cursor= database.query(dbHelper.TABLE_NAME,
                                                new String[]{ChatDatabaseHelper.KEY_MESSAGE, ChatDatabaseHelper.KEY_ID},
                                                null, null, null, null, null);
                                        messageAdapter.notifyDataSetChanged();
                                        listView.setAdapter (messageAdapter);
                                        listView.invalidateViews();
                                    }

                                } else {
                                    Log.i(ACTIVITY_NAME, "Returned to ChatActivity without result");
                                }
                            }
                        });
        if(findViewById(R.id.chatFrameLayout) != null) {
            isFramelayout = true;
        }
        dbHelper = new ChatDatabaseHelper(this);
        database = dbHelper.open();
        cursor= database.query(dbHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_MESSAGE, ChatDatabaseHelper.KEY_ID},
                null, null, null, null, null);
        int colIndex = cursor.getColumnIndex( dbHelper.KEY_MESSAGE );
        sendButton = findViewById(R.id.sendButton);
        listView = findViewById(R.id.listView);
        chatText = findViewById(R.id.chatEditText);
        Log.i(ACTIVITY_NAME, "Cursor’s  column count ="+ cursor.getColumnCount());
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" +
                    cursor.getString(cursor.getColumnIndex(dbHelper.KEY_MESSAGE)));
            String strMessage = cursor.getString(colIndex);
            String strID = cursor.getString(colIndex+1);
            System.out.println("Cursor Column Name :|"+dbHelper.KEY_MESSAGE +"| Cursor Chat Messages: |"+strMessage+"|"+"|" +"Cursor Column Name :|"+dbHelper.KEY_ID +"| Cursor ID: |"+strID+"|");
            chatMSGS.add(strMessage);
            cursor.moveToNext();
        }
        nullFieldValidator= new nullFieldValidator();
        chatText.addTextChangedListener(nullFieldValidator);
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Send Button");
                if(!nullFieldValidator.isValid()){
                    chatText.setError(getString(R.string.null_field));
                    return;
                }
                String chatMessage = chatText.getText().toString();
                chatMSGS.add(chatMessage);
                chatText.setText("");
                insertData(cursor, chatMessage);
                cursor= database.query(dbHelper.TABLE_NAME,
                        new String[]{ChatDatabaseHelper.KEY_MESSAGE, ChatDatabaseHelper.KEY_ID},
                        null, null, null, null, null);
                messageAdapter.notifyDataSetChanged();
                listView.setAdapter (messageAdapter);
                listView.invalidateViews();
             }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!cursor.isAfterLast()) {
                    Log.i("Position", String.valueOf(position));
                    id = messageAdapter.getItemId(position);
                    messageText = cursor.getString(colIndex);
                    Log.i("CursorCOuNT", String.valueOf(cursor.getCount()));
                    Log.i("ID", String.valueOf(id));
                    Bundle bundle = new Bundle();
                    bundle.putLong(dbHelper.KEY_ID, id);
                    bundle.putString(dbHelper.KEY_MESSAGE, messageText);
                    bundle.putInt("Position", position);
                    intent.putExtras(bundle);
                    if (!isFramelayout) {
                        mStartForResult.launch(intent);
                    } else {
                        ChatWindow chatWindow= new ChatWindow();
                        messageFragment= new MessageFragment().newInstance(id,messageText,chatWindow, chatMSGS, messageAdapter, listView);
                        messageFragment.setArguments(bundle);   // (1) Communicate with Fragment using Bundle
                        getSupportFragmentManager().beginTransaction().replace(R.id.chatFrameLayout, messageFragment).addToBackStack(null).commit();// begin  FragmentTransaction

                    }
                }
            }
        });
    }
    public void insertData(Cursor cursor, String chatMessage){
        cursor.moveToFirst();
        ContentValues values = new ContentValues();
        values.put( dbHelper.KEY_MESSAGE, chatMessage);
        long insertId = database.insert(dbHelper.TABLE_NAME, null,
                values);
        Log.i(ACTIVITY_NAME,"ID Inserted"+ insertId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper.open();
        Log.i(ACTIVITY_NAME, "On Resume");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        Log.i(ACTIVITY_NAME, "On Destroy");
    }
}