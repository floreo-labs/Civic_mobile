package com.civic.epoxy

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.airbnb.epoxy.EpoxyModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A pattern for using epoxy models with Kotlin with no annotations or code generation.
 *
 * See [com.airbnb.epoxy.kotlinsample.models.ItemDataClass] for a usage example.
 */
abstract class KotlinModel(
    @LayoutRes private val layoutRes: Int
) : EpoxyModel<View>() {

    private var view: View? = null

    private fun getView(): View = requireNotNull(view)

    abstract fun setData(root: View)

    open fun reset(root: View) { }

    override fun bind(view: View) {
        this.view = view
        setData(view)
    }

    override fun unbind(view: View) {
        reset(getView())
        this.view = null
    }

    override fun getDefaultLayout() = layoutRes

    protected fun <V : View> setData(@IdRes id: Int) = object : ReadOnlyProperty<KotlinModel, V> {
        override fun getValue(thisRef: KotlinModel, property: KProperty<*>): V {
            // This is not efficient because it looks up the view by id every time (it loses
            // the pattern of a "holder" to cache that look up). But it is simple to use and could
            // be optimized with a map
            @Suppress("UNCHECKED_CAST")
            return view?.findViewById(id) as V?
                ?: throw IllegalStateException("View ID $id for '${property.name}' not found.")
        }
    }
}