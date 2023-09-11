package com.yefri.routes.models

import com.yefri.routes.models.ProductJson
import kotlinx.serialization.Serializable

@Serializable
data class JsonPurchase(var date: String,
                        var unds: Int,
                        var product: ProductJson,
                        var userName: String) {
}