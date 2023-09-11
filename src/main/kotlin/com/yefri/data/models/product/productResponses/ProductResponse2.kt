package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.Model1NoAdditives
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse2 (
    @SerialName("code") val code: String,
    @SerialName("product") val product: Model1NoAdditives,
    @SerialName("status_verbose") val statusVerbose: String
) {
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}