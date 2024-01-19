package com.rishi.swipe.assignment.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
/**
 * Implementation of [ConnectivityObserver] that observes network connectivity changes using
 * [ConnectivityManager.NetworkCallback].
 *
 * @param context Application context
 */
class NetworkConnectivityObserver(
    private val context: Context
) : ConnectivityObserver {

    // ConnectivityManager instance for monitoring network changes
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Observes the network connectivity status changes using a callback flow.
     *
     * @return A [Flow] emitting [ConnectivityObserver.Status] updates.
     */
    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            // Create a NetworkCallback to handle network events
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(ConnectivityObserver.Status.Available) }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch { send(ConnectivityObserver.Status.Losing) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(ConnectivityObserver.Status.Lost) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(ConnectivityObserver.Status.Unavailable) }
                }
            }

            // Register the NetworkCallback
            connectivityManager.registerDefaultNetworkCallback(callback)

            // Unregister the NetworkCallback when the flow is closed
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}
