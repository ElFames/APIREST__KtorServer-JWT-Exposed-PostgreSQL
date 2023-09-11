package com.yefri.data.models.product.modelsFromOpenFood

import kotlinx.serialization.Serializable

@Serializable
data class Model1NotImage(
    val _id: String?,
    val code: String,
    val additives_tags: List<String>,
    val allergens: String,
    val allergens_from_ingredients: String,
    val brands: String,
    val ecoscore_grade: String,
    val product_name: String,
    val ingredients_analysis_tags: List<String>,
    val ingredients_text: String,
    val labels_old: String,
    val nutrient_levels: Map<String, String>,
    val countries_tags: List<String>,
    var quantity: String) {

    fun toJson() = com.yefri.data.models.product.modelsFromOpenFood.Model1(
        _id,
        code,
        additives_tags,
        allergens,
        allergens_from_ingredients,
        brands,
        ecoscore_grade,
        product_name,
        "",
        ingredients_analysis_tags,
        ingredients_text,
        labels_old,
        nutrient_levels,
        countries_tags,
        quantity
    )
}