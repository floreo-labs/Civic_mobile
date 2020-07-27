package com.civic.legislator

import com.civic.domain.Legislator

sealed class LegislatorState {
    object Loading : LegislatorState()
    object Error : LegislatorState()
    data class Success(val legislator: Legislator) : LegislatorState()
}