package com.civic.home

import com.civic.R
import com.civic.delegate.ComponentDelegate
import com.civic.delegate.fragment.ComponentDelegateFragment
import org.koin.core.Koin
import org.koin.core.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.inject

class HomeFragment: ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = HomeFragment::class.simpleName

        fun newInstance() = HomeFragment()
    }

    override fun getKoin(): Koin = getKoin().also {
        loadKoinModules(HomeModule.create(this))
    }

    override val delegate: ComponentDelegate by inject<HomeFragmentDelegate>()
    override val layoutId: Int
        get() = R.layout.fragment_feed
}