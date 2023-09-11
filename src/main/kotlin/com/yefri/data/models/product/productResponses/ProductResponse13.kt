package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.ModelBasic1
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse13(
    @SerialName("code") val code: String,
    @SerialName("product") val product: ModelBasic1,
    @SerialName("status_verbose") val statusVerbose: String
){
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}