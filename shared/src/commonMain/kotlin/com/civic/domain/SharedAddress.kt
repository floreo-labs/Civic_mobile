package com.civic.domain

data class SharedAddress(
    val featureName: String,
    val adminArea: String,
    val subAdminArea: String,
    val locality: String?,
    val subLocality: String,
    val thoroughFare: String,
    val subThoroughFare: String,
    val premises: String?,
    val postalCode: String,
    val countryCode: String,
    val countryName: String,
    val latitude: Double,
    val longitude: Double,
    val hasLatitude: Boolean,
    val hasLongitude: Boolean
)