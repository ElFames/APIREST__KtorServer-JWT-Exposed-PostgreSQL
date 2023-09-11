package com.yefri.data.models.store

import com.yefri.data.models.user.Users
import org.jetbrains.exposed.dao.id.IntIdTable

object Stores : IntIdTable() {
    val storeName = varchar("storeName", length = 50)
    val userId = reference("userId", Users)
}