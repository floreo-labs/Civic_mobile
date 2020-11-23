package com.civic.navigation

import androidx.fragment.app.FragmentManager
import com.civic.R
import com.civic.authentication.onboarding.OnboardingFragment
import com.civic.authentication.signup.SignupFragment
import com.civic.home.HomeFragment

class AppNavigator(private val supportFragmentManager: FragmentManager) : AppNavigation {

    override fun showOnboarding() {
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .add(R.id.activity_root_fragment_container, OnboardingFragment.newInstance(), OnboardingFragment.TAG)
            .commit()
    }

    override fun showSignup() {
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.activity_root_fragment_container, SignupFragment.newInstance(), SignupFragment.TAG)
            .commit()
    }

    override fun showFeed() {
        val onboarding = supportFragmentManager.findFragmentByTag(OnboardingFragment.TAG)
        if (onboarding != null) {
            supportFragmentManager.beginTransaction()
                .remove(onboarding)
                .commit()
        } 
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .add(R.id.activity_root_fragment_container, HomeFragment.newInstance(), HomeFragment.TAG)
            .commit()
    }
}