package com.civic.home.epoxy

import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.civic.R
import com.civic.common.android.setDistinctText
import com.civic.epoxy.KotlinHolder

@EpoxyModelClass(layout = R.layout.fragment_home_item)
abstract class HomeItemModel : EpoxyModelWithHolder<HomeItemModel.ViewHolder>() {

    @EpoxyAttribute
    lateinit var data: HomeItem

    override fun bind(holder: ViewHolder) {
        holder.setData(data)
    }

    class ViewHolder: KotlinHolder() {

        val image by bind<ImageView>(R.id.fragment_home_item_image)
        val title by bind<TextView>(R.id.fragment_home_item_title_text)

        fun setData(data: HomeItem) {
            title.setDistinctText(data.text)
        }
    }
}