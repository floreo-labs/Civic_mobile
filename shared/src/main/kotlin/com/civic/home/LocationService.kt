package com.civic.home

import android.app.Activity
import android.content.IntentSender
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.civic.arch.State
import com.civic.domain.UserLocation
import com.civic.extensions.takeIfInstance
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import java.util.Locale
import java.util.concurrent.TimeUnit

actual class LocationService(private val locationManager: LocationManager,
                             private val activity: Activity,
                             private val userLocationState: State<UserLocation>
) {

    companion object {
        const val REQUEST_LOCATION_SERVICES_RESULT_CODE = 2
    }

    private lateinit var locationProviderClient: FusedLocationProviderClient
    private lateinit var settingsClient: SettingsClient
    private lateinit var geocoder: Geocoder

    private var locationCallback: LocationCallback?= null

    /**
     * Just checks system services. Must do additional checking with Google APIs if this returns false.
     */
    val areLocationServicesEnabled: Boolean
        get() = locationManager.run {
            isProviderEnabled(LocationManager.GPS_PROVIDER) || isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

    fun init() {
        geocoder = Geocoder(activity, Locale.getDefault())
        locationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        settingsClient = LocationServices.getSettingsClient(activity)
    }

    actual fun startLocationScan() {
        stopLocationScan()

        checkIfLocationServicesAreEnabled(activity)
    }

    fun stopLocationScan() {
        if (this::locationProviderClient.isInitialized) {
            locationCallback?.let(locationProviderClient::removeLocationUpdates)
            locationCallback = null
        }
    }

    private fun checkIfLocationServicesAreEnabled(activity: Activity) {
        LocationSettingsRequest.Builder()
            .setNeedBle(false)
            .setAlwaysShow(true)
            .addLocationRequest(createLocationRequest())
            .build()
            .run(settingsClient::checkLocationSettings)
            .addOnSuccessListener { locationSettingsResponse ->
                getLastKnownLocation()
            }
            .addOnFailureListener { exception ->
                exception.takeIfInstance<ResolvableApiException> { resolvableApiException ->
                    try {
                        resolvableApiException.startResolutionForResult(
                            activity,
                            REQUEST_LOCATION_SERVICES_RESULT_CODE
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
    }

    private fun getLastKnownLocation() {
        locationProviderClient.lastLocation
            .addOnSuccessListener { lastKnownLocation ->
                if (lastKnownLocation == null) {
                    // scan for updates
                    scanForLocationUpdates()
                } else {
                    // we're good? assuming this is accurate
                    emitLocation(lastKnownLocation)
                }
            }
            .addOnFailureListener { exception ->
                val wtf = exception.localizedMessage
                // fuck do I do here
            }
    }

    private fun scanForLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val lastKnown = locationResult.lastLocation
                val locations = locationResult.locations

                emitLocation(lastKnown)
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                val wtf = locationAvailability.isLocationAvailable
            }
        }

        createLocationRequest()
            .let { locationRequest ->
                locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            }
    }

    private fun emitLocation(location: Location) {
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        if (addresses.isNotEmpty()) {
            val sharedAddress = addresses.first().run(this::toSharedAddress)
            userLocationState += sharedAddress
        }
    }

    private fun createLocationRequest() = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        .setFastestInterval(TimeUnit.SECONDS.toMillis(30))
        .setInterval(TimeUnit.SECONDS.toMillis(60))

    private fun toSharedAddress(address: Address) = address.run {
        UserLocation(
            featureName = featureName,
            adminArea = adminArea,
            subAdminArea = subAdminArea,
            locality = locality,
            subLocality = subLocality,
            thoroughFare = thoroughfare,
            subThoroughFare = subThoroughfare,
            premises = premises,
            postalCode = postalCode,
            countryCode = countryCode,
            countryName = countryName,
            latitude = latitude,
            longitude = longitude,
            hasLatitude = hasLatitude(),
            hasLongitude = hasLongitude()
        )
    }
}