package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.ModelBasic2NotImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse16(
    @SerialName("code") val code: String,
    @SerialName("product") val product: ModelBasic2NotImage,
    @SerialName("status_verbose") val statusVerbose: String
){
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}