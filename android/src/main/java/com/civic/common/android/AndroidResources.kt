package com.civic.common.android

import android.app.Application
import androidx.annotation.StringRes

class AndroidResources(private val application: Application) {

    fun getString(@StringRes stringRes: Int) = application.getString(stringRes)
}