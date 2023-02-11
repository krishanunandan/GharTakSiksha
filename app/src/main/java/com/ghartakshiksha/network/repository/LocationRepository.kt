package com.ghartakshiksha.network.repository

import com.ghartakshiksha.network.api.LocationApi
import com.ghartakshiksha.network.api.SafeApiRequest
import com.ghartakshiksha.network.response.LocationResponse

class LocationRepository(
    private val api: LocationApi
) : SafeApiRequest() {

    suspend fun getPlaceFromLatLng(
        latlng: String, key: String
    ): LocationResponse {
        return apiRequest { api.getPlacesFromLatLng(latlng, key) }
    }

}