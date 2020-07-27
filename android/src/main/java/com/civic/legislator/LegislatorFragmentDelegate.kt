package com.civic.legislator

import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.civic.R
import com.civic.delegate.ComponentDelegate

class LegislatorFragmentDelegate(
    private val model: LegislatorModel
) : ComponentDelegate() {

    val image by register<ImageView>(R.id.fragment_legislator_image)

    override fun onViewsResolved(savedState: Bundle?) {
        model.viewState { legislatorState ->
            when (legislatorState) {
                is LegislatorState.Success -> setSuccessState(legislatorState)
                LegislatorState.Loading ->  { }
                LegislatorState.Error -> { }
            }
        }
    }

    private fun setSuccessState(legislatorState: LegislatorState.Success) = with (legislatorState) {
        Glide.with(image)
            .load(legislator.imageUrl)
            .centerCrop()
            .into(image)
    }
}