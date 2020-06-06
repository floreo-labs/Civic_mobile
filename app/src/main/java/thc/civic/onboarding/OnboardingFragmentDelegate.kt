package thc.civic.onboarding

import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import thc.civic.R
import thc.civic.common.android.AndroidResources
import thc.civic.common.android.CommonAnimations
import thc.civic.common.android.Preferences
import thc.civic.delegate.ComponentDelegate
import thc.civic.navigation.Navigator
import thc.civic.onboarding.epoxy.OnboardingEpoxyController
import thc.civic.onboarding.epoxy.OnboardingItemData

class OnboardingFragmentDelegate(private val onboardingEpoxyController: OnboardingEpoxyController,
                                 private val androidResources: AndroidResources,
                                 private val commonAnimations: CommonAnimations,
                                 private val preferences: Preferences,
                                 private val navigator: Navigator) : ComponentDelegate() {

    private val viewPager by register<ViewPager2>(R.id.onboarding_view_pager)
    private val doneButton by register<Button>(R.id.onboarding_done_button)

    override fun onViewsResolved(savedState: Bundle?) {
        val titleText = androidResources.getString(R.string.onboarding_item_data_title)
        val bodyText = androidResources.getString(R.string.onboarding_item_data_description)
        onboardingEpoxyController.setData(
            listOf(
                OnboardingItemData(
                    drawableRes = R.drawable.vector_drawable_how_to_vote_black_24dp,
                    titleText = titleText,
                    bodyText = bodyText
                ),
                OnboardingItemData(
                    drawableRes = R.drawable.vector_drawable_article_black_24dp,
                    titleText = titleText,
                    bodyText = bodyText
                ),
                OnboardingItemData(
                    drawableRes = R.drawable.vector_drawable_campaign_black_24dp,
                    titleText = titleText,
                    bodyText = bodyText
                )
            )
        )

        doneButton.setOnClickListener {
            preferences.setBoolean(OnboardingConstants.HAS_SEEN_TUTORIAL, true)
            navigator.showDefaultScreen()
        }

        viewPager.adapter = onboardingEpoxyController.adapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewPager.adapter?.let { adapter ->
                    if (position == adapter.itemCount - 1) {
                        commonAnimations.fadeIn(doneButton, 600)
                    }
                }
            }
        })
    }
}