package com.civic.navigation

import com.civic.arch.NullableState
import com.civic.arch.StateModel
import com.civic.preferences.Preferences
import com.civic.preferences.PreferencesConstants
import kotlinx.coroutines.flow.filterNotNull

class NavigationModel(private val preferences: Preferences,
                    private val navigationState: NullableState<NavigationData>) : StateModel() {

    fun initialize(hasSavedState: Boolean) {
        if (hasSavedState) return

        if (preferences.getString(PreferencesConstants.SAVED_USER_KEY).isNullOrBlank()) {
            if (preferences.getBoolean(PreferencesConstants.IS_ANONYMOUS_USER) == true) {
                navigationState.plusAssign(NavigationData.Home)
            } else {
                navigationState.plusAssign(NavigationData.Auth)
            }
        } else {
            // get user, set state, go to home
            navigationState.plusAssign(NavigationData.Home)
        }
    }

    fun subscribeToNavigationState(action: suspend (NavigationData) -> Unit) {
        collectWith(navigationState.flow.filterNotNull(), action)
    }

    fun pushState(navigationData: NavigationData?) {
        navigationState += navigationData
    }
}