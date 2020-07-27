package com.civic.legislator

import androidx.fragment.app.Fragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

object LegislatorModule {

    fun create(fragment: Fragment) = module {
        val legislatorQualifier = named(LegislatorFragment.TAG)

        single { LegislatorFragmentDelegate() }
    }
}