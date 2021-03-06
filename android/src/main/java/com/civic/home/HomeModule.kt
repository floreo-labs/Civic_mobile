package com.civic.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.civic.arch.State
import com.civic.home.arch.HomeModel
import com.civic.home.arch.HomeState
import com.civic.home.epoxy.HomeEpoxyController
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

object HomeModule {

    fun create(fragment: Fragment) = module {
        single { fragment }

        single(qualifier = named(HomeFragment.TAG)) { fragment.lifecycle }

        single<CoroutineScope> { fragment.lifecycleScope }

        single { HomeEpoxyController() }

        single { HomePermissions(get()) }

        single { HomeModel(get(), State(HomeState.Empty), get(), get()) }

        single { HomeFragmentDelegate(get(), get(), get(), get(qualifier = named(HomeFragment.TAG)), get()) }
    }
}