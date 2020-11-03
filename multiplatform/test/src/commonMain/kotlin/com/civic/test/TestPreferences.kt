package com.civic.test

import com.civic.preferences.Preferences

class TestPreferences : Preferences {

    private val values = mutableMapOf<String, Any>()

    override fun setInt(key: String, value: Int) {
        values[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int = getOrElseCast(key, defaultValue)

    override fun getInt(key: String): Int? = getOrElseNull(key)

    override fun setFloat(key: String, value: Float) {
        values[key] = value
    }

    override fun getFloat(key: String, defaultValue: Float): Float = getOrElseCast(key, defaultValue)

    override fun getFloat(key: String): Float? = getOrElseNull(key)

    override fun setLong(key: String, value: Long) {
        values[key] = value
    }

    override fun getLong(key: String, defaultValue: Long): Long = getOrElseCast(key, defaultValue)

    override fun getLong(key: String): Long? = getOrElseNull(key)

    override fun setString(key: String, value: String) {
        values[key] = value
    }

    override fun getString(key: String, defaultValue: String): String = getOrElseCast(key, defaultValue)

    override fun getString(key: String): String? = getOrElseNull(key)

    override fun setBoolean(key: String, value: Boolean) {
        values[key] = value
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = getOrElseCast(key, defaultValue)

    override fun getBoolean(key: String): Boolean? = getOrElseNull(key)

    override fun remove(key: String) {
        values.remove(key)
    }

    override fun clear() {
        values.clear()
    }

    override fun hasKey(key: String): Boolean = values.containsKey(key)

    private fun <T> getOrElseCast(key: String, default: T) : T {
        return if (values.containsKey(key)) {
            @Suppress("UNCHECKED_CAST")
            values.getValue(key) as T
        } else {
            default
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getOrElseNull(key: String) = values[key] as? T
}