package com.civic.onboarding

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.civic.R
import com.civic.common.android.HighlightableClickableSpan
import com.civic.delegate.fragment.KoinFragment
import com.civic.navigation.OnboardingModel
import com.civic.onboarding.epoxy.OnboardingEpoxyController
import com.civic.onboarding.epoxy.OnboardingItemData
import com.civic.widget.ViewPagerDots
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module

class OnboardingFragment : KoinFragment(), KoinComponent {

    companion object {
        val TAG = OnboardingFragment::class.simpleName

        fun newInstance() = OnboardingFragment()
    }

    override val module: Module by lazy {
        OnboardingModule.create()
    }

    private val model by inject<OnboardingModel>()
    private val controller by inject<OnboardingEpoxyController>()

    private lateinit var signup: Button
    private lateinit var login: Button
    private lateinit var promptText: TextView
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerDots: ViewPagerDots

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_onboarding, container, false).also { root ->
            signup = root.findViewById(R.id.onboarding_signup)
            login = root.findViewById(R.id.onboarding_login)
            promptText = root.findViewById(R.id.onboarding_prompt)
            viewPager = root.findViewById(R.id.onboarding_view_pager)
            viewPagerDots = root.findViewById(R.id.onboarding_view_pager_dots)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signup.setOnClickListener {
            model.signup()
        }

        login.setOnClickListener {
            model.login()
        }

        promptText.movementMethod = LinkMovementMethod()
        promptText.text = SpannableStringBuilder().apply {
            val link = getString(R.string.onboarding_prompt_link)
            val promptWithLink = getString(R.string.onboarding_prompt_text, link)
            append(promptWithLink)
            setSpan(object: HighlightableClickableSpan(requireContext().getColor(R.color.text_link_color)) {
                override fun onClick(widget: View) {
                    model.anonymous()
                }
            }, promptWithLink.indexOf(link), promptWithLink.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        viewPager.adapter = controller.adapter
        viewPagerDots.attachTo(viewPager)
        controller.setData(createOnboardingItems())
    }

    override fun onDestroy() {
        super.onDestroy()
        model.destroy()
    }

    private fun createOnboardingItems() : List<OnboardingItemData> =
        listOf(
            OnboardingItemData(
                drawableRes = R.drawable.vector_drawable_home_black_24dp,
                titleText = getString(R.string.home),
                bodyText =  getString(R.string.onboarding_item_data_feed_description)
            ),
            OnboardingItemData(
                drawableRes = R.drawable.vector_drawable_search_black_24dp,
                titleText = getString(R.string.search),
                bodyText = getString(R.string.onboarding_item_data_search_description)
            ),
            OnboardingItemData(
                drawableRes = R.drawable.vector_drawable_priority_high_black_24dp,
                titleText = getString(R.string.Remind),
                bodyText = getString(R.string.onboarding_item_data_reminder_description)
            )
        )
}