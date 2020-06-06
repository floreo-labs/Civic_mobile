package thc.civic.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import thc.civic.R
import thc.civic.onboarding.OnboardingFragment
import thc.civic.polling.PollingFragment

class AppNavigator(private val supportFragmentManager: FragmentManager, private val activity: AppCompatActivity) : Navigator {

    override fun showOnboarding() {
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .add(R.id.activity_root_fragment_container, OnboardingFragment.newInstance(), OnboardingFragment.TAG)
            .commit()
    }

    override fun showDefaultScreen() {
        val onboarding = supportFragmentManager.findFragmentByTag(OnboardingFragment.TAG)
        if (onboarding != null) {
            supportFragmentManager.beginTransaction()
                .remove(onboarding)
                .commit()
        }
        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .add(R.id.activity_root_fragment_container, PollingFragment.newInstance(), PollingFragment.TAG)
            .commit()
    }
}