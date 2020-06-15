package com.civic.feed

import com.civic.R
import com.civic.delegate.ComponentDelegate
import com.civic.delegate.fragment.ComponentDelegateFragment
import org.kodein.di.DIAware
import org.kodein.di.android.subDI
import org.kodein.di.android.x.di
import org.kodein.di.instance

class FeedFragment: ComponentDelegateFragment(), DIAware {

    companion object {
        val TAG = FeedFragment::class.simpleName

        fun newInstance() = FeedFragment()
    }

    override val di by subDI(di()) {
        import(FeedModule.create(this@FeedFragment))
    }

    override val delegate: ComponentDelegate by instance<FeedFragmentDelegate>()
    override val layoutId: Int
        get() = R.layout.fragment_feed
}