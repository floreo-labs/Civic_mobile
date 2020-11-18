package com.civic.onboarding

import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.civic.R
import com.civic.common.android.AndroidResources
import com.civic.delegate.ComponentDelegate
import com.civic.navigation.NavigationData
import com.civic.navigation.NavigationModel
import com.civic.onboarding.epoxy.OnboardingEpoxyController
import com.civic.onboarding.epoxy.OnboardingItemData
import com.civic.preferences.Preferences
import com.civic.preferences.PreferencesConstants
import com.civic.widget.ViewPagerDots

class OnboardingFragmentDelegate(
    private val onboardingEpoxyController: OnboardingEpoxyController,
    private val androidResources: AndroidResources,
    private val preferences: Preferences,
    private val navigationModel: NavigationModel
) : ComponentDelegate() {

    private val viewPager by register<ViewPager2>(R.id.onboarding_view_pager)
    private val viewPagerDots by register<ViewPagerDots>(R.id.onboarding_view_pager_dots)
    private val doneButton by register<Button>(R.id.onboarding_done_button)
    
    override fun onViewAttached(savedState: Bundle?) {
        doneButton.setOnClickListener {
            preferences.setString(PreferencesConstants.SAVED_USER_KEY, "BRO")

            navigationModel.pushState(NavigationData.Home)
        }

        viewPager.adapter = onboardingEpoxyController.adapter
        viewPagerDots.attachTo(viewPager)
        onboardingEpoxyController.setData(createOnboardingItems())
    }

    private fun createOnboardingItems() : List<OnboardingItemData> =
        listOf(
            OnboardingItemData(
                drawableRes = R.drawable.vector_drawable_home_black_24dp,
                titleText = androidResources.getString(R.string.home),
                bodyText =  androidResources.getString(R.string.onboarding_item_data_feed_description)
            ),
            OnboardingItemData(
                drawableRes = R.drawable.vector_drawable_search_black_24dp,
                titleText = androidResources.getString(R.string.search),
                bodyText = androidResources.getString(R.string.onboarding_item_data_search_description)
            ),
            OnboardingItemData(
                drawableRes = R.drawable.vector_drawable_priority_high_black_24dp,
                titleText = androidResources.getString(R.string.Remind),
                bodyText = androidResources.getString(R.string.onboarding_item_data_reminder_description)
            )
        )
}