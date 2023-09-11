package com.yefri.routes.models

import com.yefri.routes.models.ProductJson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ShopListProduct")
class ShopListJsonProduct(val shopListId: String, val product: ProductJson, var quantity: Int)