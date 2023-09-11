package com.yefri.data.models.product.modelsFromOpenFood

import kotlinx.serialization.Serializable

@Serializable
data class Model5NoLabels(
    val _id: String?,
    val code: String,
    val additives_tags: List<String>,
    val allergens: String,
    val allergens_from_ingredients: String,
    val brands: String,
    val generic_name: String,
    val image_url: String,
    val ingredients_analysis_tags: List<String>,
    val ingredients_text: String,
    val nutrient_levels: Map<String, String>,
    val countries_tags: List<String>,
    val quantity: String,
) {
    fun toJson() = com.yefri.data.models.product.modelsFromOpenFood.Model1(
        _id,
        code,
        additives_tags,
        allergens,
        allergens_from_ingredients,
        brands,
        "",
        generic_name,
        image_url,
        ingredients_analysis_tags,
        ingredients_text,
        "",
        nutrient_levels,
        countries_tags,
        quantity
    )
}