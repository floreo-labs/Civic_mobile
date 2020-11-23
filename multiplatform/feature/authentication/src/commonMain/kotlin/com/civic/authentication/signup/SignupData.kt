package com.civic.authentication.signup

sealed class SignupData {

    enum class ErrorType {
        EMAIL, PASSWORD
    }

    object SubmissionValidating : SignupData()

    object SubmissionValidated : SignupData()

    /**
     * TODO: Contextualization of strings in multiplatform module. Should really just be done on the server
     */
    data class SubmissionDenied(val errors: List<Pair<ErrorType, String>>) : SignupData()
}