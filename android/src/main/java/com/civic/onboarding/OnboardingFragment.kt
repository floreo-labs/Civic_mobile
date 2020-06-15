package com.civic.onboarding

import com.civic.R
import com.civic.delegate.fragment.ComponentDelegateFragment
import org.kodein.di.DIAware
import org.kodein.di.android.subDI
import org.kodein.di.android.x.di
import org.kodein.di.instance

class OnboardingFragment : ComponentDelegateFragment(), DIAware {

    companion object {
        val TAG = OnboardingFragment::class.simpleName

        fun newInstance() = OnboardingFragment()
    }

    override val di by subDI(di()) {
        import(OnboardingModule.create())
    }

    override val delegate by instance<OnboardingFragmentDelegate>()

    override val layoutId: Int
        get() = R.layout.fragment_onboarding
}