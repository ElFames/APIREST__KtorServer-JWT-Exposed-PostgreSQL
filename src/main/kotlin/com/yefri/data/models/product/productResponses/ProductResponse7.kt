package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.Model3
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ProductResponse7(
    @SerialName("code") val code: String,
    @SerialName("product") val product: Model3,
    @SerialName("status_verbose") val statusVerbose: String) {

    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}