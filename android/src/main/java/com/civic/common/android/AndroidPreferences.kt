package com.civic.common.android

import android.content.Context
import android.content.SharedPreferences
import com.civic.preferences.Preferences

class AndroidPreferences(context: Context, name: String) : Preferences {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    //region int
    override fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }
    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }
    override fun getInt(key: String): Int? {
        return if(hasKey(key)){
            sharedPreferences.getInt(key, 0)
        } else {
            null
        }
    }
    //endregion

    //region float
    override fun setFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }
    override fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }
    override fun getFloat(key: String): Float? {
        return if(hasKey(key)){
            sharedPreferences.getFloat(key, 0f)
        } else {
            null
        }
    }
    //endregion

    //region long
    override fun setLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }
    override fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }
    override fun getLong(key: String): Long? {
        return if(hasKey(key)){
            sharedPreferences.getLong(key, 0)
        } else {
            null
        }
    }
    //endregion

    //region string
    override fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }
    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
    override fun getString(key: String): String? {
        return if(hasKey(key)){
            sharedPreferences.getString(key, "")
        } else {
            null
        }
    }
    //endregion

    //region boolean
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
    override fun getBoolean(key: String): Boolean? {
        return if(hasKey(key)){
            sharedPreferences.getBoolean(key, false)
        } else {
            null
        }
    }
    override fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }
    //endregion

    override fun hasKey(key: String): Boolean = sharedPreferences.contains(key)

    override fun clear(){
        sharedPreferences.edit().clear().apply()
    }

    override fun remove(key: String) {
        if(hasKey(key))
            sharedPreferences.edit().remove(key).apply()
    }
}