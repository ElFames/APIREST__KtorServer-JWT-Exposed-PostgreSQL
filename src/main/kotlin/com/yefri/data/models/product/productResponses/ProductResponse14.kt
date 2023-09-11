package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.ModelBasic1NotImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse14(
    @SerialName("code") val code: String,
    @SerialName("product") val product: ModelBasic1NotImage,
    @SerialName("status_verbose") val statusVerbose: String
){
    fun toProductResponse() = ProductResponse1(
        code,
        product.toJson(),
        statusVerbose
    )
}