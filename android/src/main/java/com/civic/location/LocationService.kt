package com.civic.location

import android.app.Activity

interface LocationService {

    val areLocationServicesEnabled: Boolean

    fun init(activity: Activity)
    fun startLocationScan()
    fun stopLocationScan()
}