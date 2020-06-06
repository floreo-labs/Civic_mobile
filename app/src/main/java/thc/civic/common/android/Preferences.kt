package thc.civic.common.android

import android.content.SharedPreferences

class Preferences(private val sharedPreferences: SharedPreferences) {

    fun getBoolean(name: String, default: Boolean = false) = sharedPreferences.getBoolean(name, default)

    fun setBoolean(name: String, value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(name, value)
            .apply()
    }
}