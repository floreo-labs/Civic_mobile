package com.civic.legislator

import androidx.core.os.bundleOf
import com.civic.R
import com.civic.common.android.extensions.getStringArgument
import com.civic.delegate.ComponentDelegate
import com.civic.delegate.fragment.ComponentDelegateFragment
import com.civic.domain.Legislator
import com.civic.extensions.nonNullSimpleName
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module

class LegislatorFragment: ComponentDelegateFragment(), KoinComponent {

    companion object {
        val TAG = LegislatorFragment::class.nonNullSimpleName

        private const val LEGISLATOR_ID_KEY = "LegislatorIdKey"

        fun newInstance(legislator: Legislator) = LegislatorFragment().apply {
            arguments = bundleOf(LEGISLATOR_ID_KEY to legislator.id)
        }
    }

    override fun createModule(): Module =
        LegislatorModule.create(fragment = this,
        legislatorId = getStringArgument(LEGISLATOR_ID_KEY))

    override val delegate: ComponentDelegate by inject<LegislatorFragmentDelegate>()

    override val layoutId: Int
        get() = R.layout.fragment_legislator
}