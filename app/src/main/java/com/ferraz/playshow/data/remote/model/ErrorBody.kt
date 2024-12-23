package com.ferraz.playshow.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val errorMessage: String
) {

    override fun toString(): String {
        return "Error: $statusCode => $errorMessage"
    }
}
