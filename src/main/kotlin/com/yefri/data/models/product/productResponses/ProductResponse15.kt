package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.ModelBasic2
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse15(
    @SerialName("code") val code: String,
    @SerialName("product") val product: ModelBasic2,
    @SerialName("status_verbose") val statusVerbose: String
){
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}