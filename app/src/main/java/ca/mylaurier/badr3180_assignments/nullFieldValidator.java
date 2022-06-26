package ca.mylaurier.badr3180_assignments;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.Pattern;

public class nullFieldValidator implements TextWatcher{
    private boolean mIsValid = false;
    public boolean isValid() {
        return mIsValid;
    }
    public static boolean isValidtext(CharSequence pwd) {
        return pwd != null && pwd != "";
    }
    @Override
    final public void afterTextChanged(Editable editableText) {
        mIsValid = isValidtext(editableText);
    }
    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*No-op*/}
    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {/*No-op*/}
}
