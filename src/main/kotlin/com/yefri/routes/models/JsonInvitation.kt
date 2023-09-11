package com.yefri.routes.models

import kotlinx.serialization.Serializable

@Serializable
data class JsonInvitation(val id: Int, val userSender: String, val userHost: String) {
}