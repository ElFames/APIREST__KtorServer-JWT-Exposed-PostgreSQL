package com.yefri.data.models.relations.userStores

import com.yefri.data.models.store.Stores
import com.yefri.data.models.user.Users
import org.jetbrains.exposed.dao.id.IntIdTable

object UsersStores : IntIdTable() {
    val storeId = reference("storeId", Stores)
    val userId = reference("userId", Users)
}