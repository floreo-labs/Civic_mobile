package com.civic.navigation

import androidx.fragment.app.FragmentManager
import com.civic.R
import com.civic.domain.Legislator
import com.civic.home.HomeFragment
import com.civic.legislator.LegislatorFragment
import com.civic.onboarding.OnboardingFragment

class AppNavigator(private val supportFragmentManager: FragmentManager) : Navigator {

    override fun showOnboarding() {
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

    override fun showLegislatorDetail(legislator: Legislator) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(LegislatorFragment.TAG)
            .replace(R.id.activity_root_fragment_container, LegislatorFragment.newInstance(), LegislatorFragment.TAG)
            .commit()
    }
}