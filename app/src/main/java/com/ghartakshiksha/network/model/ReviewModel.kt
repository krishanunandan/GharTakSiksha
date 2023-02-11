package com.ghartakshiksha.network.model

data class ReviewModel(
    val CreatedOn: String,
    val ImageLink: String,
    val Name: String,
    val Price: String,
    val ProfileImageLink: String,
    val Rating: Double,
    val Review: String,
    val Title: String,
    val CustomerID: Int
)