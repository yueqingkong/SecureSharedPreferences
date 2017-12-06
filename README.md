## SecureSharedPreferences
a simple android library for SharedPreferences that support to encrypt values with AES.

# Usage
```
SecurePreferences securePreferences = new SecurePreferences(activity, shareName, passWord, 0);

securePreferences.edit().putString(stringKey, "stringKey").apply();
String stringValue = securePreferences.getString(stringKey, "");
```