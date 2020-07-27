package com.civic.onboarding

import com.civic.R
import com.civic.delegate.fragment.ComponentDelegateFragment
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module

class OnboardingFragment : ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = OnboardingFragment::class.simpleName

        fun newInstance() = OnboardingFragment()
    }

    override val delegate by inject<OnboardingFragmentDelegate>()

    override fun createModule(): Module = OnboardingModule.create()

    override val layoutId: Int
        get() = R.layout.fragment_onboarding
}