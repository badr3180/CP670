package ca.mylaurier.badr3180_assignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
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
ArrayList<Editable> chatMSGS= new ArrayList<android.text.Editable>();


private class ChatAdapter extends ArrayAdapter<String>{
    public ChatAdapter(Context ctx) {
        super(ctx, 0);
        Log.d("ChatAdapter", "comes to constructor");

    }

    @Override
    public int getCount() {
        Log.d("chatAdaptergetCount", "size: "+chatMSGS.size());
        return chatMSGS.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        Log.d("ChatMessageGetItem", "getItem: "+chatMSGS.get(position));
        return String.valueOf(chatMSGS.get(position));
    }
    @Nullable
    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
        Log.d("chatAdaptergetView", "in View");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        sendButton = findViewById(R.id.sendButton);
        listView = findViewById(R.id.listView);
        chatText = findViewById(R.id.chatEditText);
        ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Send Button");
                chatMSGS.add(chatText.getText());
                messageAdapter.notifyDataSetChanged(); //this restarts the process of
                chatText.setText("");
             }
        });
    }
}