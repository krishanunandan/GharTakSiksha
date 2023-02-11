package com.ghartakshiksha.network.model

data class LocationModel(
    val formatted_address: String,
    val address_components: ArrayList<AddressComponents>
)