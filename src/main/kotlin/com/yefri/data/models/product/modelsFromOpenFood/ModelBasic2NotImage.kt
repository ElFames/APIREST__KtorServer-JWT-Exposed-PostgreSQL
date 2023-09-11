package com.yefri.data.models.product.modelsFromOpenFood

import kotlinx.serialization.Serializable

@Serializable
data class ModelBasic2NotImage(
    val _id: String?,
    val code: String,
    val allergens: String,
    val generic_name: String,
    val nutrient_levels: Map<String, String>,
    val origins: List<String>
) {
    fun toJson() = com.yefri.data.models.product.modelsFromOpenFood.Model1(
        _id,
        code,
        listOf(),
        allergens,
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
