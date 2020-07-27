package com.civic.legislator

import com.apollographql.apollo.ApolloClient
import com.civic.arch.State
import com.civic.arch.StateModel
import com.civic.domain.Legislator
import com.civic.queries.LegislatorDetailQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LegislatorModel(
    coroutineScope: CoroutineScope,
    private val workerContext: CoroutineContext,
    private val apolloClient: ApolloClient,
    private val viewState: State<LegislatorState>,
    private val legislatorId: String) : StateModel(coroutineScope) {

    fun viewState(onUpdate: suspend (LegislatorState) -> Unit) {
        coroutineScope.launch(context = workerContext) {
            val flow = apolloClient.query(LegislatorDetailQuery(legislatorId)).execute()
                .onStart {
                    LegislatorState.Loading
                }
                .catch {
                    LegislatorState.Error
                }
                .map { response ->
                    response.data!!.mapToViewState()
                }
            collectWith(flow) { legislatorState ->
                viewState += legislatorState
            }
        }

        collectWith(viewState.flow.filterNotNull(), onUpdate)
    }

    private fun LegislatorDetailQuery.Data.mapToViewState() = LegislatorState.Success(
        legislator = this.person!!.run {
            Legislator(id = legislatorId,
            name = name!!,
            imageUrl = image!!.replace("http://", "https://"))
        }
    )
}