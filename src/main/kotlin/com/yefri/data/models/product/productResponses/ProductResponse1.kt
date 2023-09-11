package com.yefri.data.models.product.productResponses

import com.yefri.data.models.product.modelsFromOpenFood.Model1
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse1(
    @SerialName("code") val code: String,
    @SerialName("product") val product: com.yefri.data.models.product.modelsFromOpenFood.Model1,
    @SerialName("status_verbose") val statusVerbose: String
)