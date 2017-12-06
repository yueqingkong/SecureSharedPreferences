package com.testsecureshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import secure.share.SecurePreferences;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "_MainActivity";
    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testShare();
            }
        });
    }

    private String stringKey = "string";
    private String intKey = "int";
    private String longKey = "long";
    private String floatKey = "float";
    private String boolKey = "bool";

    public void testShare() {
        activity = this;
        String shareName = "shareName";
        String passWord = "qwerty";

        SecurePreferences securePreferences = new SecurePreferences(activity, shareName, passWord, 0);

        securePreferences.edit().putString(stringKey, "stringKey").apply();
        securePreferences.edit().putInt(intKey, 2).apply();
        securePreferences.edit().putLong(longKey, 3).apply();
        securePreferences.edit().putFloat(floatKey, 4F).apply();
        securePreferences.edit().putBoolean(boolKey, true).apply();

        String stringValue = securePreferences.getString(stringKey, "");
        int intValue = securePreferences.getInt(intKey, 0);
        long longValue = securePreferences.getLong(longKey, 0L);
        float floatValue = securePreferences.getFloat(floatKey, 0F);
        boolean boolValue = securePreferences.getBoolean(boolKey, false);

        Log.d(TAG, stringValue + "; " + String.valueOf(intValue) + "; "
                + String.valueOf(longValue) + "; " + String.valueOf(floatValue) + "; "
                + String.valueOf(boolValue));
    }
}
