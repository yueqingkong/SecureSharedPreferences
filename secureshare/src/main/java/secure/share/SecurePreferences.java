package secure.share;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import secure.share.inter.InterEncry;
import secure.share.inter.NormalEncry;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SecurePreferences implements SharedPreferences {

    private SharedPreferences sharedPreferences;
    private InterEncry interEncry;

    public SecurePreferences(Context context, String sharename, String password, int mode) {
        this(context, sharename, password, mode, "AES");
    }

    public SecurePreferences(Context context, String sharename, String password, int mode, String algorithm) {
        if (TextUtils.isEmpty(sharename)) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        } else {
            sharedPreferences = context.getSharedPreferences(sharename, mode);
            // 选择你需要的加解密模型
            interEncry = new NormalEncry(algorithm, password);
        }
    }

    @Override
    public Map<String, ?> getAll() {
        final Map<String, ?> stringMap = sharedPreferences.getAll();
        final Map<String, String> hashMap = new HashMap<>();
        for (Map.Entry<String, ?> entry : stringMap.entrySet()) {
            Object cipherText = entry.getValue();
            if (cipherText != null) {
                String decryKey = interEncry.decrypt(entry.getKey());
                String decryValue = interEncry.decrypt(entry.getValue().toString());
                hashMap.put(decryKey, decryValue);
            }
        }
        return hashMap;
    }

    @Override
    public String getString(String key, String defValue) {
        String encryKey = interEncry.encrypt(key);
        String string = sharedPreferences.getString(encryKey, defValue);
        return string.equals(defValue) ? string : interEncry.decrypt(string);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        Set<String> stringSet = new HashSet<>();
        for (String value : sharedPreferences.getStringSet(key, defValues)) {
            stringSet.add(interEncry.decrypt(value));
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
        String encryKey = interEncry.encrypt(key);
        return sharedPreferences.contains(encryKey);
    }

    @Override
    public Editor edit() {
        return new SecureEditor(sharedPreferences, interEncry);
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
