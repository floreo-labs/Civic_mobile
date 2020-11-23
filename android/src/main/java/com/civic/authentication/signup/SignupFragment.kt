package com.civic.authentication.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.civic.R
import com.civic.common.android.extensions.textAsString
import com.civic.common.android.fragment.KoinFragment
import com.civic.common.extensions.exhaust
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.inject
import org.koin.core.module.Module

class SignupFragment : KoinFragment() {

    companion object {
        val TAG = SignupFragment::class.simpleName

        fun newInstance() = SignupFragment()
    }

    override val module: Module by lazy {
        SignupModule.create()
    }

    private val model by inject<SignupModel>()

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var submitButton: CircularProgressButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_signup, container, false).also { root ->
            emailInput = root.findViewById(R.id.signup_input_email)
            passwordInput = root.findViewById(R.id.signup_input_password)
            submitButton = root.findViewById(R.id.signup_submit)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitButton.setOnClickListener {
            val email = emailInput.textAsString
            val password = emailInput.textAsString
            model.validate(email, password)
        }

        lifecycleScope.launchWhenStarted {
            model.signupFlow
                .onEach { signupData ->
                    when (signupData) {
                        SignupData.SubmissionValidating -> {
                            submitButton.startAnimation()
                        }
                        SignupData.SubmissionValidated -> {
                            submitButton.revertAnimation()
                            emailInput.error = null
                            passwordInput.error = null
                        }
                        is SignupData.SubmissionDenied -> {
                            submitButton.revertAnimation()
                            signupData.errors.forEach { (errorType, message) ->
                                when (errorType) {
                                    SignupData.ErrorType.EMAIL -> {
                                        emailInput.error = message
                                    }
                                    SignupData.ErrorType.PASSWORD -> {
                                        passwordInput.error = message
                                    }
                                }.exhaust
                            }
                        }
                    }.exhaust
                }.launchIn(this)
        }
    }
}