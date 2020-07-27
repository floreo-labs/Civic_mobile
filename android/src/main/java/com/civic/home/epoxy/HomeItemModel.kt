package com.civic.home.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.civic.R
import com.civic.common.android.setDistinctText
import com.civic.epoxy.KotlinModel
import com.civic.navigation.AppNavigation

data class HomeItemModel(private val data: HomeItem, private val navigation: AppNavigation) : KotlinModel(R.layout.fragment_home_item) {

    val image by setData<ImageView>(R.id.fragment_home_item_image)
    val title by setData<TextView>(R.id.fragment_home_item_title_text)

    override fun setData(root: View) {
        root.setOnClickListener {

        }

        title.setDistinctText(data.text)

        Glide.with(image)
            .load(data.imageUrl)
            .centerCrop()
            .into(image)
    }

    override fun reset(root: View) {
        root.setOnClickListener(null)
    }
}