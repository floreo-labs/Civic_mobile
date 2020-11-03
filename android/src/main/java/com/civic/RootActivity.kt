package com.civic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.civic.di.extensions.loadModule
import com.civic.di.extensions.unloadModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {

    private val module by lazy {
        ActivityModule.create(this)
    }
    private val delegate by inject<RootActivityDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        getKoin().loadModule(module)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        delegate.bindViews(findViewById(R.id.activity_root), savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.activity_root_fragment_container)?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()

        getKoin().unloadModule(module)
        delegate.onViewDetached()
    }
}