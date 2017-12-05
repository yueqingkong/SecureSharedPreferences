package secure.share;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SecureShare {

    private SecurePreferences securePreferences;
    private SecureEditor secureEditor;

    public void register(Context context) {
        String shareName = "shareName";
        String passWord = "passWord";

        SharedPreferences preferences = context.getSharedPreferences(shareName, 0);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.apply();
    }
}
