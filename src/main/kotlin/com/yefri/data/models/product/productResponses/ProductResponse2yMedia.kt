package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.Model1NoIngredients
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse2yMedia (
    @SerialName("code") val code: String,
    @SerialName("product") val product: Model1NoIngredients,
    @SerialName("status_verbose") val statusVerbose: String
) {
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}