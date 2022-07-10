package ca.mylaurier.badr3180_assignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
public class DBReadWriteTest {
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private ChatWindow chatWindow;

    @Before
    public void createDb() {
                dbHelper = new ChatDatabaseHelper(ApplicationProvider.getApplicationContext());
                        //InstrumentationRegistry.getInstrumentation().getTargetContext());
        db = dbHelper.open();
    }
    @After
    public void finish() {
        dbHelper.close();
    }
    @Test
    public void testPreConditions() {
        assertNotNull(db);
    }
    @Test
    public void testShouldAddMessage() throws Exception {
        Cursor cursor= db.query(dbHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null);
        cursor.moveToFirst();
        ContentValues values = new ContentValues();
        values.put( dbHelper.KEY_MESSAGE, "UnitTestMessage");
        long insertId = db.insert(dbHelper.TABLE_NAME, null,
                values);
        assertThat(insertId, is(1L));
    }

    @Test
    public void testUpgrade() {
        dbHelper.onUpgrade(db,1,2);
    }

    @Test
    public void testDowngrade() {
        dbHelper.onDowngrade(db,2,1);
    }

}