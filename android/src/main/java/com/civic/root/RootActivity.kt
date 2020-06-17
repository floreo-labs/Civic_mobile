package com.civic.root

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.civic.R
import org.koin.core.KoinComponent
import org.koin.core.inject

class RootActivity : AppCompatActivity(), KoinComponent {

    private val delegate by inject<RootActivityDelegate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val koin = getKoin()
        val scope = koin.getOrCreateScope<AppCompatActivity>("Activity")
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