package com.brandon.communication_app

import com.google.gson.annotations.SerializedName

data class Message (
    @SerializedName("message")
    val message: String?
)