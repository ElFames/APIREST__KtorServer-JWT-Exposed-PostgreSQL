package com.yefri.routes.models

import com.yefri.routes.models.StoreJsonProduct
import kotlinx.serialization.Serializable

@Serializable
data class JsonStore(var storeId: Int, var storeName: String, var userId: String, var products: MutableList<StoreJsonProduct>) {
}