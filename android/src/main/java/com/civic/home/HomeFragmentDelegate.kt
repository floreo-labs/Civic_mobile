package com.civic.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.recyclerview.widget.RecyclerView
import com.civic.R
import com.civic.common.android.extensions.setAllGone
import com.civic.common.android.extensions.setAllVisible
import com.civic.delegate.ComponentDelegate
import com.civic.extensions.exhaust
import com.civic.home.arch.HomeModel
import com.civic.home.arch.HomeState
import com.civic.home.epoxy.HomeEpoxyController

class HomeFragmentDelegate(
    private val homePermissions: HomePermissions,
    private val homeEpoxyController: HomeEpoxyController,
    private val fragment: Fragment,
    private val homeModel: HomeModel
) : ComponentDelegate(), DefaultLifecycleObserver {

    private val viewStateEmptyGroup by register<Group>(R.id.fragment_feed_empty_group)
    private val loading by register<View>(R.id.fragment_feed_loading)
    private val recycler by register<RecyclerView>(R.id.fragment_feed_recycler)
    private val root by register<ConstraintLayout>(R.id.fragment_feed_root)
    private val enableLocationCta by register<View>(R.id.fragment_feed_empty_enable_location)

    override fun onViewsResolved(savedState: Bundle?) {
        homeModel.viewState { feedState ->
            when (feedState) {
                HomeState.Empty -> showEmptyState()
                HomeState.Loading -> showLoadingState()
                HomeState.ShowPermissionUI -> showPermissionsUI()
                is HomeState.Success -> showSuccessState(feedState)
                HomeState.Error -> showErrorState()
            }.exhaust
        }
        homeModel.enableLocationServices()

        recycler.adapter = homeEpoxyController.adapter

        enableLocationCta.setOnClickListener {
            homeModel.enableLocationServices()
        }
    }

    override fun onViewsCleared() {
        recycler.adapter = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LocationService.REQUEST_LOCATION_SERVICES_RESULT_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> homeModel.getCurrentLocation()
                Activity.RESULT_CANCELED -> homeModel.onPermissionDenied()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (homePermissions.isPermissionGranted(requestCode, permissions, grantResults)) {
            homeModel.enableLocationServices()
        } else {
            homeModel.onPermissionDenied()
        }
    }

    private fun showErrorState() {
        loading.isVisible = false

        root.setBackgroundColor(Color.RED)
    }

    private fun showLoadingState() {
        viewStateEmptyGroup.setAllGone(root)

        loading.isVisible = true
    }

    private fun showEmptyState() {
        viewStateEmptyGroup.setAllVisible(root)

        loading.isVisible = false
    }

    private fun showSuccessState(sucesss: HomeState.Success) {
        viewStateEmptyGroup.setAllGone(root)

        loading.isVisible = false

        homeEpoxyController.setData(sucesss.legislators)
    }

    private fun showPermissionsUI() {
        loading.isVisible = true

        fragment.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            HomePermissions.LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}