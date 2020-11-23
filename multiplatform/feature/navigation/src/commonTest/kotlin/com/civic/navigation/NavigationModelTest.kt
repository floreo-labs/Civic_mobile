package com.civic.navigation

import com.civic.preferences.PreferencesConstants
import com.civic.test.TestPreferences
import kotlin.test.BeforeTest
import kotlin.test.Test

class NavigationModelTest {

    private lateinit var preferences: TestPreferences
    private lateinit var navigationState: NullableState<NavigationData>
    private lateinit var navigationModel: NavigationModel

    @BeforeTest
    fun setup() {
        preferences = TestPreferences()
        navigationState = NullableState()
        navigationModel = NavigationModel(preferences, navigationState)
    }

    @Test
    fun `should not push to state if has already initialized`() {
        val previousState = navigationState.value

        navigationModel.initialize(hasSavedState = true)
        val newState = navigationState.value

        previousState assertEquals newState
    }

    @Test
    fun `should navigate to home if no saved user and is anonymous`() {
        preferences.setString(PreferencesConstants.SAVED_USER_KEY, "")
        preferences.setBoolean(PreferencesConstants.IS_ANONYMOUS_USER, true)

        navigationModel.initialize(hasSavedState = false)

        navigationState.value.assertNotNullIsA<NavigationData.Home>()
    }

    @Test
    fun `should navigate to auth if no saved user but is not anonymous`() {
        preferences.setString(PreferencesConstants.SAVED_USER_KEY, "")
        preferences.setBoolean(PreferencesConstants.IS_ANONYMOUS_USER, false)

        navigationModel.initialize(hasSavedState = false)

        navigationState.value.assertNotNullIsA<NavigationData.Auth>()
    }

    @Test
    fun `should navigate to home if has saved user`() {
        preferences.setString(PreferencesConstants.SAVED_USER_KEY, "something")

        navigationModel.initialize(hasSavedState = false)

        navigationState.value.assertNotNullIsA<NavigationData.Home>()
    }

    @Test
    fun `should push to state`() {
        val newState = NavigationData.Home

        navigationModel.pushState(newState)

        navigationState.value assertEquals newState
    }
}