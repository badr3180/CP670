package ca.mylaurier.badr3180_assignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
protected static final String ACTIVITY_NAME = "ChatActivity";
Button sendButton;
EditText chatText;
ListView listView;
ArrayList<String> chatMSGS= new ArrayList<String>();
private nullFieldValidator nullFieldValidator;
private SQLiteDatabase database;
private ChatDatabaseHelper dbHelper;

private class ChatAdapter extends ArrayAdapter<String>{
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
        TextView message = result.findViewById(R.id.message_text);
        message.setText(getItem(position)); // get the string at position
        return result;
    }
}

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        dbHelper = new ChatDatabaseHelper(this);
        database = dbHelper.open();
        Cursor cursor= database.query(dbHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_MESSAGE},
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
            System.out.println("Cursor Column Name :|"+dbHelper.KEY_MESSAGE +"| Cursor Chat Messages: |"+strMessage+"|");
            chatMSGS.add(strMessage);
            cursor.moveToNext();
        }
        nullFieldValidator= new nullFieldValidator();
        chatText.addTextChangedListener(nullFieldValidator);
        ChatAdapter messageAdapter =new ChatAdapter( this );
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
                messageAdapter.notifyDataSetChanged();
                chatText.setText("");
                insertData(cursor, chatMessage);
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