package com.civic.feed.arch

sealed class FeedState {
    object Empty: FeedState()
    object Loading: FeedState()
    object ShowPermissionUI: FeedState()
    data class Success(val id: Int = 0) : FeedState()
}