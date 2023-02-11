package com.ghartakshiksha.network.model


data class Message(
    var MessageID: Int,
    var isNew: Int,
    var Name: String,
    var ProfileImageLink: String,
    var UserID: Int,
    var UserType: String,
    var Message: String,
    var CreatedOn: String,
    var MessageSide: String
)
