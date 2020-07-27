package com.civic.legislator

import com.civic.R
import com.civic.delegate.ComponentDelegate
import com.civic.delegate.fragment.ComponentDelegateFragment
import com.civic.extensions.nonNullSimpleName
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module

class LegislatorFragment: ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = LegislatorFragment::class.nonNullSimpleName

        fun newInstance() = LegislatorFragment()
    }

    override val module: Module by lazy {
        LegislatorModule.create(this)
    }
    override val delegate: ComponentDelegate by inject<LegislatorFragmentDelegate>()

    override val layoutId: Int
        get() = R.layout.fragment_legislator
}