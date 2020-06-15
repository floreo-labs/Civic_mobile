package com.civic.home

interface HomePermissions {
    fun hasLocationPermission() : Boolean
    fun shouldShowPermissionUI(): Boolean
    fun isPermissionGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) : Boolean
}