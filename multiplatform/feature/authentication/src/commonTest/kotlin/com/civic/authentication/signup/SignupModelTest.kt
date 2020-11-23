package com.civic.authentication.signup

import com.civic.arch.State
import com.civic.test.assertions.assertElementAtIndex
import com.civic.test.assertions.assertEquals
import com.civic.test.assertions.assertNotNullIsA
import com.civic.test.assertions.assertSize
import kotlin.test.Test

private const val VALID_EMAIL = "miles@goat.com"
private const val VALID_PASSWORD = "Cowboys69"

class SignupModelTest {

    private val state = State<SignupData>()
    private val model = SignupModel(state)

    @Test
    fun `should be a valid submission`() {
        model.validate(VALID_EMAIL, VALID_PASSWORD)

        state.value.assertNotNullIsA<SignupData.SubmissionValidated>()
    }

    @Test
    fun `should return invalid password type`() {
        val password = "butt"

        model.validate(VALID_EMAIL, password)

        state.value.assertNotNullIsA<SignupData.SubmissionDenied> { submissionDenied ->
            submissionDenied.errors assertSize 1
            submissionDenied.errors.assertElementAtIndex(0) { pair ->
                pair.first assertEquals SignupData.ErrorType.PASSWORD
                pair.second assertEquals "Your password must be at least 8 characters."
            }
        }
    }

    @Test
    fun `should return invalid email type`() {
        val email = "@something"

        model.validate(email, VALID_PASSWORD)

        state.value.assertNotNullIsA<SignupData.SubmissionDenied> { submissionDenied ->
            submissionDenied.errors assertSize 1
            submissionDenied.errors.assertElementAtIndex(0) { pair ->
                pair.first assertEquals SignupData.ErrorType.EMAIL
                pair.second assertEquals "Not a valid email."
            }
        }
    }
}