package com.civic.home

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.civic.R
import com.civic.common.android.extensions.setAllGone
import com.civic.common.android.extensions.setAllVisible
import com.civic.common.android.fragment.KoinFragment
import com.civic.common.extensions.exhaust
import com.civic.common.extensions.nonNullSimpleName
import com.civic.home.arch.HomeModel
import com.civic.home.arch.HomeState
import com.civic.home.epoxy.HomeEpoxyController
import com.civic.home.epoxy.HomeItem
import org.koin.core.inject
import org.koin.core.module.Module

class HomeFragment: KoinFragment() {

    companion object {
        val TAG = HomeFragment::class.nonNullSimpleName

        fun newInstance() = HomeFragment()
    }

    override val module: Module by lazy {
        HomeModule.create(this)
    }

    private val homePermissions by inject<HomePermissions>()
    private val homeEpoxyController by inject<HomeEpoxyController>()
    private val homeModel by inject<HomeModel>()

    private lateinit var viewStateEmptyGroup: Group
    private lateinit var loading: View
    private lateinit var recycler: RecyclerView
    private lateinit var rootView: ConstraintLayout
    private lateinit var enableLocationCta: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_home, container, false).also { root ->
            viewStateEmptyGroup = root.findViewById(R.id.fragment_feed_empty_group)
            loading  = root.findViewById(R.id.fragment_feed_loading)
            recycler  = root.findViewById (R.id.fragment_feed_recycler)
            rootView = root.findViewById(R.id.fragment_feed_root)
            enableLocationCta = root.findViewById(R.id.fragment_feed_empty_enable_location)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (homePermissions.isPermissionGranted(requestCode, permissions, grantResults)) {
            homeModel.enableLocationServices()
        } else {
            homeModel.onPermissionDenied()
        }
    }

    private fun showErrorState() {
        loading.isVisible = false

        rootView.setBackgroundColor(Color.RED)
    }

    private fun showLoadingState() {
        viewStateEmptyGroup.setAllGone(rootView)

        loading.isVisible = true
    }

    private fun showEmptyState() {
        viewStateEmptyGroup.setAllVisible(rootView)

        loading.isVisible = false
    }

    private fun showSuccessState(sucesss: HomeState.Success) {
        viewStateEmptyGroup.setAllGone(rootView)

        loading.isVisible = false

        val items = sucesss.legislators.map { legislator ->
            HomeItem(
                imageUrl = legislator.imageUrl,
                text = legislator.name
            )
        }
        homeEpoxyController.setData(items)
    }

    private fun showPermissionsUI() {
        loading.isVisible = true

        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            HomePermissions.LOCATION_PERMISSION_REQUEST_CODE
        )
    }
}