package com.civic

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.civic.common.android.CommonAnimations
import com.civic.delegate.ComponentDelegate
import com.civic.home.LocationService
import com.civic.navigation.AppNavigation
import com.civic.onboarding.OnboardingConstants
import com.civic.onboarding.OnboardingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class RootActivityDelegate(private val supportFragmentManager: FragmentManager,
                           private val lifecycle: Lifecycle,
                           private val deviceLocation: LocationService,
                           private val fadeAnimation: CommonAnimations,
                           private val sharedPreferences: SharedPreferences,
                           private val appNavigation: AppNavigation) : ComponentDelegate(), DefaultLifecycleObserver {

    private val bottomNav by register<BottomNavigationView>(R.id.activity_root_bottom_nav)
    private val toolbar by register<Toolbar>(R.id.activity_root_toolbar)

    private var lifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks ?= null

    override fun onViewsResolved(savedState: Bundle?) {
        lifecycle.addObserver(this)
        if (savedState == null) {
            if (sharedPreferences.getBoolean(OnboardingConstants.HAS_SEEN_TUTORIAL, false)) {
                appNavigation.showFeed()
            } else {
                appNavigation.showOnboarding()
            }

            initBottomNavBar()
            subscribeToFragmentLifecycleCallbacks()
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        deviceLocation.init()
    }

    override fun onPause(owner: LifecycleOwner) {
        deviceLocation.stopLocationScan()
    }

    override fun unbind() {
        super.unbind()

        lifecycle.removeObserver(this)
        lifecycleCallbacks?.let(supportFragmentManager::unregisterFragmentLifecycleCallbacks)
        deviceLocation.stopLocationScan()
    }

    private fun initBottomNavBar() {
    }

    private fun subscribeToFragmentLifecycleCallbacks() {
        lifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                if (f.tag == OnboardingFragment.TAG) {
                    fadeAnimation.fadeOut(bottomNav, 300)
                    fadeAnimation.fadeOut(toolbar, 300)
                }
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                if (f.tag == OnboardingFragment.TAG) {
                    fadeAnimation.fadeIn(bottomNav, 300)
                    fadeAnimation.fadeIn(toolbar, 300)
                }
            }
        }.also { supportFragmentManager.registerFragmentLifecycleCallbacks(it, false) }
    }
}