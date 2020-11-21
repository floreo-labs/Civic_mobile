package com.civic.common.android.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.civic.di.extensions.loadModule
import com.civic.di.extensions.unloadModule
import org.koin.core.KoinComponent
import org.koin.core.module.Module

abstract class KoinFragment : Fragment(), KoinComponent {

    abstract val module: Module

    override fun onAttach(context: Context) {
        getKoin().loadModule(module)
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        getKoin().unloadModule(module)
    }
}