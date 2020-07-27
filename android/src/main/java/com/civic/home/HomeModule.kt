package com.civic.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.civic.arch.State
import com.civic.home.arch.HomeModel
import com.civic.home.arch.HomeState
import com.civic.home.epoxy.HomeEpoxyController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

object HomeModule {

    fun create(fragment: Fragment) = module {
        val homeQualifier = named(HomeFragment.TAG)

        single(qualifier = homeQualifier) { fragment }

        single(qualifier = homeQualifier) { fragment.lifecycle }

        single<CoroutineScope>(qualifier = homeQualifier) { fragment.lifecycleScope }

        single { HomeEpoxyController(navigation = get()) }

        single { HomePermissions(fragment = get(qualifier = homeQualifier)) }

        single { HomeModel(coroutineScope = get(qualifier = homeQualifier),
            workerContext = Dispatchers.IO,
            apolloClient = get(),
            userLocationState = get(),
            viewState = State(HomeState.Empty),
            homePermissions = get(),
            locationService = get()) }

        single { HomeFragmentDelegate(
            homePermissions = get(),
            homeEpoxyController = get(),
            fragment = get(qualifier = homeQualifier),
            homeModel = get()
        ) }
    }
}