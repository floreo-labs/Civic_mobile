package com.civic.feed

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.civic.arch.State
import com.civic.feed.arch.FeedModel
import com.civic.feed.arch.FeedState
import com.civic.feed.epoxy.FeedEpoxyController
import org.kodein.di.*

object FeedModule {

    fun create(fragment: Fragment) = DI.Module(name = "FeedModule") {
        bind() from provider { fragment }

        bind() from provider { instance<Fragment>().lifecycle }

        bind<CoroutineScope>() with provider { instance<Fragment>().lifecycleScope }

        bind() from singleton { FeedEpoxyController() }

        bind() from provider { AndroidFeedPermissions(instance()) }

        bind() from singleton {
            FeedModel(instance(), Dispatchers.IO, instance(), State(FeedState.Empty), instance(), instance())
        }

        bind() from provider { FeedFragmentDelegate(instance(), instance(), instance(), instance()) }
    }
}