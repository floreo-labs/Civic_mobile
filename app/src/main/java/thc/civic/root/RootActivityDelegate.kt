package thc.civic.root

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import thc.civic.R
import thc.civic.delegate.ComponentDelegate
import thc.civic.onboarding.OnboardingFragment
import thc.civic.common.android.CommonAnimations
import thc.civic.common.android.Preferences
import thc.civic.navigation.Navigator
import thc.civic.onboarding.OnboardingConstants

class RootActivityDelegate(private val supportFragmentManager: FragmentManager,
    private val fadeAnimation: CommonAnimations,
    private val preferences: Preferences,
    private val navigator: Navigator) : ComponentDelegate() {

    private val bottomNav by register<BottomNavigationView>(R.id.activity_root_bottom_nav)
    private val toolbar by register<Toolbar>(R.id.activity_root_toolbar)

    override fun onViewsResolved(savedState: Bundle?) {
        if (savedState == null) {
            if (preferences.getBoolean(OnboardingConstants.HAS_SEEN_TUTORIAL)) {
                navigator.showDefaultScreen()
            } else {
                navigator.showOnboarding()
            }

            initBottomNavBar()
            subscribeToFragmentLifecycleCallbacks()
        }
    }

    private fun initBottomNavBar() {
        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            val menuItemId = menuItem.itemId
            true
        }
    }

    private fun subscribeToFragmentLifecycleCallbacks() {
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
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
        }, false)
    }
}