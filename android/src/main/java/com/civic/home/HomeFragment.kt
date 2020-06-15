package com.civic.home

import com.civic.R
import com.civic.delegate.ComponentDelegate
import com.civic.delegate.fragment.ComponentDelegateFragment
import org.kodein.di.DIAware
import org.kodein.di.android.subDI
import org.kodein.di.android.x.di
import org.kodein.di.instance

class HomeFragment: ComponentDelegateFragment(), DIAware {

    companion object {
        val TAG = HomeFragment::class.simpleName

        fun newInstance() = HomeFragment()
    }

    override val di by subDI(di()) {
        import(HomeModule.create(this@HomeFragment))
    }

    override val delegate: ComponentDelegate by instance<HomeFragmentDelegate>()
    override val layoutId: Int
        get() = R.layout.fragment_feed
}