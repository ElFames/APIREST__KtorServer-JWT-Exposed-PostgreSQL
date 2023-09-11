package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.Model1NoLabels
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse1yMedio(
    @SerialName("code") val code: String,
    @SerialName("product") val product: Model1NoLabels,
    @SerialName("status_verbose") val statusVerbose: String
) {
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}