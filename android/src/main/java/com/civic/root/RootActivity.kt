package com.civic.root

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.civic.R
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.kodein.di.android.di
import org.kodein.di.android.subDI

class RootActivity : AppCompatActivity(), DIAware {

    override val di by subDI(di()) {
        import(ActivityModule.create(this@RootActivity))
    }

    private val delegate by instance<RootActivityDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_root)

        delegate.bind(findViewById(R.id.activity_root), savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.activity_root_fragment_container)?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()

        delegate.unbind()
    }
}