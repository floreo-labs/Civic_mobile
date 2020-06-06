package thc.civic.delegate

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewPropertyDelegate<ViewType: View>(@IdRes private val viewId: Int): ReadOnlyProperty<Any, ViewType> {

    private var view: ViewType? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): ViewType =
        requireNotNull(view) {
            "You must call resolve before accessing a view property"
        }

    fun clear() {
        view = null
    }

    fun resolve(root: View) {
        view = root.findViewById<ViewType>(viewId)
    }
}