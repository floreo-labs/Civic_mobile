package com.civic.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import com.civic.arch.State
import com.civic.home.arch.HomeModel
import com.civic.home.arch.HomeState
import com.civic.home.epoxy.HomeEpoxyController
import org.kodein.di.*

object HomeModule {

    fun create(fragment: Fragment) = DI.Module(name = "FeedModule") {
        bind() from provider { fragment }

        bind() from provider { instance<Fragment>().lifecycle }

        bind<CoroutineScope>() with provider { instance<Fragment>().lifecycleScope }

        bind() from singleton { HomeEpoxyController() }

        bind() from provider { AndroidHomePermissions(instance()) }

        bind() from singleton {
            HomeModel(instance(), Dispatchers.IO, instance(), State(HomeState.Empty), instance(), instance())
        }

        bind() from provider { HomeFragmentDelegate(instance(), instance(), instance(), instance()) }
    }
}