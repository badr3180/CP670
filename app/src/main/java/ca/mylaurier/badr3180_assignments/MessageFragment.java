package ca.mylaurier.badr3180_assignments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ChatWindow chatWindow;
    public ChatWindow.ChatAdapter messageAdapter;
    public ListView listView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    long ID;
    String message;
    int position;
    ArrayList<String> ChatMsgs= new ArrayList<String>();


    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.

     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public MessageFragment newInstance(long param1, String param2, ChatWindow chatWindow1, ArrayList<String> chatMsgs, ChatWindow.ChatAdapter messageAdapter, ListView listView) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        Log.i("Class", String.valueOf(chatWindow1));
        fragment.chatWindow = chatWindow1;
        fragment.ChatMsgs = chatMsgs;
        fragment.messageAdapter= messageAdapter;
        fragment.listView = listView;
        Log.i("Class", String.valueOf(chatWindow));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            message = getArguments().getString("Message", "NoMessage");
            ID = getArguments().getLong("_id", 00);
            position = getArguments().getInt("Position");
            Log.i("PARAM1", message);
            Log.i("PARAM2", String.valueOf(ID));
            Log.i("Class in create", String.valueOf(this.chatWindow));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        TextView messageTXT = view.findViewById(R.id.fragmentMessageText);
        messageTXT.setText(message);
        TextView idTXT = view.findViewById(R.id.fragmentMessageID);
        idTXT.setText(String.valueOf(ID));
        Button deleteBTN = (Button) view.findViewById(R.id.deleteMessage);
        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ChatWIndowObject", String.valueOf(chatWindow));
                if (chatWindow == null) {
                    Log.i("If Null", String.valueOf(chatWindow));
                    Log.i("Delete Button clicked ", String.valueOf(ID));
                    Intent resultIntent = new Intent();
                    long response = ID;
                    resultIntent.putExtra("_id", response);
                    resultIntent.putExtra("Position", position);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
                else{
                    Log.i("TabletFragment", String.valueOf(ID));
                    ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(getActivity().getBaseContext());
                    Log.i("Delete position", String.valueOf(position));
                    Log.i("ID DELTE", String.valueOf(ID));
                    SQLiteDatabase database;
                    database = dbHelper.open();
                    Cursor cursor;
                    cursor= database.query(dbHelper.TABLE_NAME,
                            new String[]{ChatDatabaseHelper.KEY_MESSAGE, ChatDatabaseHelper.KEY_ID},
                            null, null, null, null, null);
                    dbHelper.deleteItem(ID);
                    ChatMsgs.remove(position);
                    cursor= database.query(dbHelper.TABLE_NAME,
                            new String[]{ChatDatabaseHelper.KEY_MESSAGE, ChatDatabaseHelper.KEY_ID},
                            null, null, null, null, null);
                    messageAdapter.notifyDataSetChanged();
                    listView.setAdapter(messageAdapter);
                    listView.invalidateViews();
                    getActivity().onBackPressed();
                }
                }

        });
        return view;
    }
}