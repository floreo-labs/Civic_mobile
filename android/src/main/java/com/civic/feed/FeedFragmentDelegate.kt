package com.civic.feed

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.civic.R
import com.civic.common.android.extensions.setAllGone
import com.civic.common.android.extensions.setAllVisible
import com.civic.extensions.exhaust
import com.civic.delegate.ComponentDelegate
import com.civic.feed.arch.FeedModel
import com.civic.feed.arch.FeedState
import com.civic.location.DeviceLocation
import android.Manifest
import android.widget.Toast

class FeedFragmentDelegate(
    private val feedPermissions: FeedPermissions,
    private val fragment: Fragment,
    private val lifecycle: Lifecycle,
    private val feedModel: FeedModel
) : ComponentDelegate(), DefaultLifecycleObserver {

    private val viewStateEmptyGroup by register<Group>(R.id.fragment_feed_empty_group)
    private val recycler by register<RecyclerView>(R.id.fragment_feed_recycler)
    private val root by register<ConstraintLayout>(R.id.fragment_feed_root)
    private val enableLocationCta by register<View>(R.id.fragment_feed_empty_enable_location)

    override fun onViewsResolved(savedState: Bundle?) {
        lifecycle.addObserver(this)

        feedModel.viewState { feedState ->
            when (feedState) {
                FeedState.Empty -> showEmptyState()
                FeedState.Loading -> showLoadingState()
                FeedState.ShowPermissionUI -> showPermissionsUI()
                is FeedState.Success -> showSuccessState()
            }.exhaust
        }
        feedModel.enableLocationServices()

        enableLocationCta.setOnClickListener {
            feedModel.enableLocationServices()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        lifecycle.removeObserver(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DeviceLocation.REQUEST_LOCATION_SERVICES_RESULT_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> feedModel.getCurrentLocation()
                Activity.RESULT_CANCELED -> feedModel.onPermissionDenied()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (feedPermissions.isPermissionGranted(requestCode, permissions, grantResults)) {
            feedModel.enableLocationServices()
        } else {
            feedModel.onPermissionDenied()
        }
    }

    private fun showLoadingState() {
        viewStateEmptyGroup.setAllGone(root)
    }

    private fun showEmptyState() {
        viewStateEmptyGroup.setAllVisible(root)
    }

    private fun showSuccessState() {
        viewStateEmptyGroup.setAllGone(root)

        Toast.makeText(viewStateEmptyGroup.context, "GOT LOCATION", Toast.LENGTH_SHORT).show()
    }

    private fun showPermissionsUI() {
        fragment.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            AndroidFeedPermissions.LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}