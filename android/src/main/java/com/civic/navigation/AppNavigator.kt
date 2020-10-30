package com.civic.navigation

import androidx.fragment.app.FragmentManager
import com.civic.R
import com.civic.feature.home.HomeFragment
import com.civic.feature.onboarding.OnboardingFragment

class AppNavigator(private val supportFragmentManager: FragmentManager) : AppNavigation {

    override fun showAuth() {
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .add(R.id.activity_root_fragment_container, OnboardingFragment.newInstance(), OnboardingFragment.TAG)
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