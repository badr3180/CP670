package ca.mylaurier.badr3180_assignments;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "Messages";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE  = "Message";
    protected static final String CLASS_NAME = "ChatDatabaseHelper";
    private SQLiteDatabase db;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + KEY_ID
            + " integer primary key  autoincrement, " + KEY_MESSAGE
            + " text not null);";


    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i(ChatDatabaseHelper.class.getName(),
                "Calling onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ChatDatabaseHelper.class.getName(),
                "Downgrading database from version " + newVersion + " to "
                        + oldVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  SQLiteDatabase open() throws SQLException {
        db= this.getWritableDatabase();
        Log.i(CLASS_NAME, "DataBase Opened");
        return db;
    }
    public void close() {
        db.close();
        Log.i(CLASS_NAME, "DataBase Closed");
    }

    public void deleteItem(long ID) {
        Log.i("At Delete", String.valueOf(ID));
        db.delete(TABLE_NAME, KEY_ID
                + " = " + ID, null);
        Log.i(CLASS_NAME, "deleted item "+ID);
    }
}
