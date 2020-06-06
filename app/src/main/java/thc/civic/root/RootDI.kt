package thc.civic.root

import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import org.koin.dsl.module
import thc.civic.common.android.CommonAnimations
import thc.civic.navigation.AppNavigator
import thc.civic.navigation.Navigator
import kotlin.math.sin

fun rootModule(activity: AppCompatActivity) = module {
    single { activity }

    single { activity.supportFragmentManager }

    single<Navigator> { AppNavigator(get(), get()) }

    factory { RootActivityDelegate(get(), get(), get(), get()) }
}