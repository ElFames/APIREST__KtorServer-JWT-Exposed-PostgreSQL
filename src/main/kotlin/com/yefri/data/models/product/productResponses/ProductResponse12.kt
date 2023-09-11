package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.Model5NoAdditives
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse12(
    @SerialName("code") val code: String,
    @SerialName("product") val product: Model5NoAdditives,
    @SerialName("status_verbose") val statusVerbose: String
){
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}