package com.civic.navigation

sealed class OnboardingData {

    object SignUp : OnboardingData()

    object Login : OnboardingData()

    object Anonymous : OnboardingData()
}