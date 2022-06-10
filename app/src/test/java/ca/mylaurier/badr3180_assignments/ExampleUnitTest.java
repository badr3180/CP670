package ca.mylaurier.badr3180_assignments;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void onCreate() {
        assertNotNull(MainActivity.ACTIVITY_NAME);
    }
    @Test
    public void onIntentStartChar(){
        assertNotNull(ChatWindow.ACTIVITY_NAME);
    }

}