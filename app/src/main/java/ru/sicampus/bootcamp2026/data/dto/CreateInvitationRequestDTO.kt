package ru.sicampus.bootcamp2026.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateInvitationRequestDTO(
    @SerialName ("meetingId") val meetingId: Int,
    @SerialName ("employeeUsername") val employeeUsername: String,
    @SerialName ("message") val message: String
)
