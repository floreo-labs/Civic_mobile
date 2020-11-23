package com.civic.authentication.signup

import com.civic.arch.State
import com.civic.arch.StateModel
import kotlinx.coroutines.flow.Flow

class SignupModel(private val signupState: State<SignupData>) : StateModel() {

    val signupFlow: Flow<SignupData>
        get() = signupState.flow

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        // Stolen from https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
        private val VALID_EMAIL_REGEX = Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    }

    fun validate(email: String, password: String) {
        signupState.update(SignupData.SubmissionValidating)

        val errors = mutableListOf<Pair<SignupData.ErrorType, String>>()
        if (password.length < MIN_PASSWORD_LENGTH) {
            errors += Pair(SignupData.ErrorType.PASSWORD, "Your password must be at least 8 characters.")
        }
        if (VALID_EMAIL_REGEX.matches(email).not()) {
            errors += Pair(SignupData.ErrorType.EMAIL, "Not a valid email.")
        }

        if (errors.isEmpty()) {
            signupState.update(SignupData.SubmissionValidated)
        } else {
            signupState.update(SignupData.SubmissionDenied(errors))
        }
    }
}