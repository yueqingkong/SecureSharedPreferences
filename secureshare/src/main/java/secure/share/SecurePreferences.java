package secure.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SecurePreferences implements SharedPreferences {

    private SharedPreferences sharedPreferences;
    private EncryBean encryBean;

    public SecurePreferences(Context context, String sharename, String password, int mode) {
        if (TextUtils.isEmpty(sharename)) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            sharedPreferences = context.getSharedPreferences(sharename, mode);
            encryBean = new EncryBean(password);
        }
    }

    @Override
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    @Override
    public String getString(String key, String defValue) {
        return encryBean.decrypt(sharedPreferences.getString(key, defValue));
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        Set<String> stringSet = new HashSet<>();
        for (String value : sharedPreferences.getStringSet(key, defValues)) {
            stringSet.add(encryBean.decrypt(value));
        }
        return stringSet;
    }

    @Override
    public int getInt(String key, int defValue) {
        return Integer.parseInt(getString(key, String.valueOf(defValue)));
    }

    @Override
    public long getLong(String key, long defValue) {
        return Long.parseLong(getString(key, String.valueOf(defValue)));
    }

    @Override
    public float getFloat(String key, float defValue) {
        return Float.parseFloat(getString(key, String.valueOf(defValue)));
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(getString(key, String.valueOf(defValue)));
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public Editor edit() {
        return new SecureEditor(sharedPreferences, encryBean);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
