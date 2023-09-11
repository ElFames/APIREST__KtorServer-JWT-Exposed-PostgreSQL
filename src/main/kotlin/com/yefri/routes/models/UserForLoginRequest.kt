package com.yefri.routes.models

import kotlinx.serialization.Serializable

@Serializable
data class UserForLoginRequest(val username: String, val password: String)