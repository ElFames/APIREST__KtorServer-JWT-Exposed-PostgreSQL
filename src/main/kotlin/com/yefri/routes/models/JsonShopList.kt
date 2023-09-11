package com.yefri.routes.models

import kotlinx.serialization.Serializable

@Serializable
class JsonShopList(val shopListId: Int, var shopListName: String, var userId: String, var products: MutableList<ShopListJsonProduct>) {
}