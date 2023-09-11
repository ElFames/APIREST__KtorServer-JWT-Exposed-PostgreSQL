package com.yefri.data.models.shopList

import com.yefri.data.models.user.Users
import org.jetbrains.exposed.dao.id.IntIdTable

object ShopLists : IntIdTable() {
    val shopListName = varchar("shopListName", length = 50)
    val userId = reference("userId", Users)
}