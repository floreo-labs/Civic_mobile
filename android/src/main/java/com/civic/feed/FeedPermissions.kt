package com.civic.feed

interface FeedPermissions {
    fun hasLocationPermission() : Boolean
    fun shouldShowPermissionUI(): Boolean
    fun isPermissionGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) : Boolean
}