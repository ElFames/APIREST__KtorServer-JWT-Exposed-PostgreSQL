package com.yefri.data.models.product.modelsFromOpenFood

import kotlinx.serialization.Serializable


@Serializable
data class ModelBasic1NotImage(
    val _id: String?,
    val code: String,
    val product_name: String,
    val brands: String,
    val image_url: String,
    val nutrient_levels: Map<String, String>,
    val countries_tags: List<String>
) {
    fun toJson() = com.yefri.data.models.product.modelsFromOpenFood.Model1(
        _id,
        code,
        listOf(),
        "",
        "",
        brands,
        "",
        product_name,
        "",
        listOf(),
        "",
        "",
        nutrient_levels,
        countries_tags,
        ""
    )
}
