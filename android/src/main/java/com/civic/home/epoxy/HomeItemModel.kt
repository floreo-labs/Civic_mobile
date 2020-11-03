package com.civic.home.epoxy

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.civic.R
import com.civic.common.android.setDistinctText
import com.civic.epoxy.KotlinModel

data class HomeItemModel(private val data: HomeItem) : KotlinModel(R.layout.fragment_home_item) {

    val image by bind<ImageView>(R.id.fragment_home_item_image)
    val title by bind<TextView>(R.id.fragment_home_item_title_text)

    override fun bind() {
        title.setDistinctText(data.text)

        Glide.with(image)
            .load(data.imageUrl)
            .centerCrop()
            .into(image)
    }
}