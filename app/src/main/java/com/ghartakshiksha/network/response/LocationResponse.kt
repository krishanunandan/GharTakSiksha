package com.ghartakshiksha.network.response

import com.ghartakshiksha.network.model.LocationModel

data class LocationResponse(
    val status:String?,
    val results: ArrayList<LocationModel>?
)