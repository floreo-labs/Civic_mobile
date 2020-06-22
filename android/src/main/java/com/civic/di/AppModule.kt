package com.civic.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.ApolloHttpNetworkTransport
import com.apollographql.apollo.network.HttpMethod
import com.civic.BuildConfig
import com.civic.arch.State
import com.civic.domain.UserLocation
import org.koin.dsl.module

object AppModule {

    fun create() = module {
        single { State<UserLocation?>(null) }

        single {
            ApolloClient(
                networkTransport = ApolloHttpNetworkTransport(
                    serverUrl = "https://openstates.org/graphql",
                    headers = mapOf(
                        "Accept" to "application/json",
                        "Content-Type" to "application/json",
                        "X-API-KEY" to BuildConfig.OPEN_STATES
                    ),
                    httpMethod = HttpMethod.Post
                )
            )
        }
    }
}