package secure.share;

import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashSet;
import java.util.Set;

import secure.share.inter.InterEncry;

/**
 * Created by Administrator on 2017/12/5.
 */
public class SecureEditor implements SharedPreferences.Editor {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private InterEncry encrypt;

    public SecureEditor(SharedPreferences preferences, InterEncry bean) {
        this.sharedPreferences = preferences;
        this.editor = sharedPreferences.edit();
        this.encrypt = bean;
    }

    @Override
    public SharedPreferences.Editor putString(String key, String value) {
        String encryKey = encrypt.encrypt(key);
        String encryValue = encrypt.encrypt(value);
        editor.putString(encryKey, encryValue);
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
        Set<String> stringSet = new HashSet<>();
        for (String value : values) {
            String encryValue = encrypt.encrypt(value);
            stringSet.add(encryValue);
        }

        String encryKey = encrypt.encrypt(key);
        editor.putStringSet(encryKey, stringSet);
        return this;
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        return putString(key, Integer.toString(value));
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        return putString(key, Long.toString(value));
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        return putString(key, Float.toString(value));
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        return putString(key, Boolean.toString(value));
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        editor.remove(key);
        return this;
    }

    @Override
    public SharedPreferences.Editor clear() {
        editor.clear();
        return this;
    }

    @Override
    public boolean commit() {
        return editor.commit();
    }

    @Override
    public void apply() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
