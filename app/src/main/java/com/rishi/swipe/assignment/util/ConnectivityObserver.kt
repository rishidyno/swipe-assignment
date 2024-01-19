package com.rishi.swipe.assignment.util

import kotlinx.coroutines.flow.Flow

/**
 * Interface for observing network connectivity status changes.
 */
interface ConnectivityObserver {

    /**
     * Observes the network connectivity status changes.
     *
     * @return A [Flow] emitting [Status] updates.
     */
    fun observe(): Flow<Status>

    /**
     * Enum representing the possible network connectivity statuses.
     */
    enum class Status {
        /**
         * The network is available.
         */
        Available,

        /**
         * The network is unavailable.
         */
        Unavailable,

        /**
         * The network is losing connectivity.
         */
        Losing,

        /**
         * The network is lost.
         */
        Lost
    }
}