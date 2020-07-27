package com.civic.legislator

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.civic.arch.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LegislatorModule {

    fun create(fragment: Fragment, legislatorId: String) = module {
        val legislatorQualifier = named(LegislatorFragment.TAG)

        single(qualifier = legislatorQualifier) { fragment }

        single<CoroutineScope>(qualifier = legislatorQualifier) {
            fragment.lifecycleScope
        }

        single {
            LegislatorModel(
                coroutineScope = get(qualifier = legislatorQualifier),
                workerContext = Dispatchers.IO,
                apolloClient = get(),
                viewState = State(initial = null),
                legislatorId = legislatorId
            )
        }

        single { LegislatorFragmentDelegate(
            model = get()
        ) }
    }
}