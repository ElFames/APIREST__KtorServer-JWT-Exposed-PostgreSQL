package com.yefri.data.models.invitations

import com.yefri.data.models.user.Users
import org.jetbrains.exposed.dao.id.IntIdTable

object Invitations : IntIdTable() {
    val userSender = reference("userSender", Users)
    val userHost = reference("userHost", Users)
}