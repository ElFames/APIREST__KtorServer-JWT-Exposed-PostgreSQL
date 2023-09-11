package com.yefri.data.models.product.modelsFromOpenFood

import kotlinx.serialization.Serializable

@Serializable
data class ModelBasic2(
    val _id: String?,
    val code: String,
    val generic_name: String,
    val brands: String,
    val image_url: String,
    val nutrient_levels: Map<String, String>,
    val origins: List<String>
) {
    fun toJson() = com.yefri.data.models.product.modelsFromOpenFood.Model1(
        _id,
        code,
        listOf(),
        "",
        "",
        "",
        "",
        generic_name,
        "",
        listOf(),
        "",
        "",
        nutrient_levels,
        origins,
        ""
    )
}