package com.civic.authentication.signup

import com.civic.arch.State
import org.koin.dsl.module

object SignupModule {

    fun create() = module {
        single { SignupModel(signupState = State()) }
    }
}