package com.civic.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

actual class HomePermissions(private val fragment: Fragment) {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private val coarsePermission = Manifest.permission.ACCESS_COARSE_LOCATION
    private val finePermission = Manifest.permission.ACCESS_FINE_LOCATION

    actual fun hasLocationPermission() : Boolean {
        val isGranted : (String) -> Boolean = { permission ->
            ContextCompat.checkSelfPermission(fragment.requireContext(), permission) == PackageManager.PERMISSION_GRANTED
        }
        return isGranted(coarsePermission) && isGranted(finePermission)
    }

    fun shouldShowPermissionUI() =
        ActivityCompat.shouldShowRequestPermissionRationale(fragment.requireActivity(), finePermission)

    fun isPermissionGranted(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) : Boolean {
        val isGranted: (Int, String) -> Boolean = { index, permission ->
            permissions.getOrNull(index) == permission &&
                    grantResults.getOrNull(index) == PackageManager.PERMISSION_GRANTED
        }
        return requestCode == LOCATION_PERMISSION_REQUEST_CODE && isGranted(0, coarsePermission) && isGranted(1, finePermission)
    }
}