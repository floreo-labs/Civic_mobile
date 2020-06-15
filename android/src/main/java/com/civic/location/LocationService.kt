package com.civic.location

interface LocationService {

    val areLocationServicesEnabled: Boolean

    fun init()
    fun startLocationScan()
    fun stopLocationScan()
}