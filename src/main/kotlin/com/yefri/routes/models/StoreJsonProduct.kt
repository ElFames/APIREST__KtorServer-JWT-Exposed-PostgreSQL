package com.yefri.routes.models

import com.yefri.routes.models.ProductJson
import kotlinx.serialization.Serializable

@Serializable
class StoreJsonProduct(val storeId: String, val product: ProductJson, var quantity: Int)