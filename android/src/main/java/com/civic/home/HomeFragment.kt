package com.civic.home

import com.civic.R
import com.civic.delegate.ComponentDelegate
import com.civic.delegate.fragment.ComponentDelegateFragment
import com.civic.extensions.nonNullSimpleName
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module

class HomeFragment: ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = HomeFragment::class.nonNullSimpleName

        fun newInstance() = HomeFragment()
    }

    override val module: Module by lazy {
        HomeModule.create(this)
    }
    override val delegate: ComponentDelegate by inject<HomeFragmentDelegate>()

    override val layoutId: Int
        get() = R.layout.fragment_home
}