package com.yefri.data.models.product

import com.yefri.routes.models.ProductJson
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Product(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Product>(Products)

    var code by com.yefri.data.models.product.Products.code
    var productName by com.yefri.data.models.product.Products.productName
    var brands by com.yefri.data.models.product.Products.brands
    var price by com.yefri.data.models.product.Products.price
    var points by com.yefri.data.models.product.Products.points
    var sugar by com.yefri.data.models.product.Products.sugar
    var fat by com.yefri.data.models.product.Products.fat
    var saturatedFat by com.yefri.data.models.product.Products.saturatedFat
    var salt by com.yefri.data.models.product.Products.salt
    var palmOil by com.yefri.data.models.product.Products.palmOil
    var additives by com.yefri.data.models.product.Products.additives
    var allergens by com.yefri.data.models.product.Products.allergens
    var vegan by com.yefri.data.models.product.Products.vegan
    var vegetarian by com.yefri.data.models.product.Products.vegetarian
    var celiaco by com.yefri.data.models.product.Products.celiaco
    var ecoscoreGrade by com.yefri.data.models.product.Products.ecoscoreGrade
    var origins by com.yefri.data.models.product.Products.origins
    var quantity by com.yefri.data.models.product.Products.quantity
    var ingredientsText by com.yefri.data.models.product.Products.ingredientsText
    var imageUrl by com.yefri.data.models.product.Products.imageUrl
    var imageNutrimentsUrl by com.yefri.data.models.product.Products.imageNutrimentsUrl
    var imageIngredientsUrl by com.yefri.data.models.product.Products.imageIngredientsUrl

    fun toJson() = ProductJson(
        id.value,
        code,
        productName?:"Nombre Desconocido",
        brands?:"Marca Desconocida",
        price?:0.0,
        points?:0,
        sugar?:0,
        fat?:0,
        saturatedFat?:0,
        salt?:0,
        palmOil?:0,
        additives?:0,
        allergens?:"Desconocido",
        vegan,
        vegetarian,
        celiaco,
        ecoscoreGrade?:"Desconocida",
        origins?:"Desconocido",
        quantity?:"Desconocida",
        ingredientsText?:"Desconocidos",
        imageUrl?:"",
        imageNutrimentsUrl?:"",
        imageIngredientsUrl?:""
    )
}
