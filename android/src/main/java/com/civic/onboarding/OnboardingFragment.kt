package com.civic.onboarding

import com.civic.R
import com.civic.delegate.fragment.ComponentDelegateFragment
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject
import org.koin.core.module.Module

class OnboardingFragment : ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = OnboardingFragment::class.simpleName

        fun newInstance() = OnboardingFragment()
    }

    override fun getKoin(): Koin = getKoin().also {
        loadKoinModules(OnboardingModule.create())
    }

    override val delegate by inject<OnboardingFragmentDelegate>()
    override val module: Module by lazy {
        OnboardingModule.create()
    }

    override val layoutId: Int
        get() = R.layout.fragment_onboarding
}