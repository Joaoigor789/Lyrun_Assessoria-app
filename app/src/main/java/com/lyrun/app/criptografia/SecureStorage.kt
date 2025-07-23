package com.lyrun.app.util

import android.content.Context
import android.content.SharedPreferences
import javax.crypto.SecretKey

class SecureStorage(context: Context, private val secretKey: SecretKey) {

    private val prefsName = "secure_prefs"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    @Synchronized
    fun saveData(key: String, plainText: String) {
        val encrypted = CryptoUtil.encrypt(plainText, secretKey)
        sharedPreferences.edit().putString(key, encrypted).apply()
    }

    @Synchronized
    fun getData(key: String): String? {
        val encrypted = sharedPreferences.getString(key, null) ?: return null
        return try {
            CryptoUtil.decrypt(encrypted, secretKey)
        } catch (e: Exception) {
            null
        }
    }

    @Synchronized
    fun removeData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    @Synchronized
    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
