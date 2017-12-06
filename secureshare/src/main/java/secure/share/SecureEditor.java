package secure.share;

import android.content.SharedPreferences;
import android.os.Build;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/5.
 */
public class SecureEditor implements SharedPreferences.Editor {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private EncryBean encryBean;

    public SecureEditor(SharedPreferences preferences, EncryBean bean) {
        this.sharedPreferences = preferences;
        this.editor = sharedPreferences.edit();
        this.encryBean = bean;
    }

    @Override
    public SharedPreferences.Editor putString(String key, String value) {
        editor.putString(key, encryBean.encrypt(value));
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, Set<String> values) {
        Set<String> stringSet = new HashSet<>();
        for (String value : values) {
            stringSet.add(encryBean.encrypt(value));
        }
        editor.putStringSet(key, stringSet);
        return this;
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        editor.putString(key, encryBean.encrypt(Integer.toString(value)));
        return this;
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        editor.putString(key, encryBean.encrypt(Long.toString(value)));
        return this;
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        editor.putString(key, encryBean.encrypt(Float.toString(value)));
        return this;
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        editor.putString(key, encryBean.encrypt(Boolean.toString(value)));
        return this;
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
            commit();
        }
    }
}
