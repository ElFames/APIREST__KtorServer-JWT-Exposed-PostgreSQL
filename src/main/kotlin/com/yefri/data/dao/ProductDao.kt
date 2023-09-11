package com.yefri.data.dao

import com.yefri.data.models.product.Product
import com.yefri.routes.models.ProductJson
import com.yefri.data.models.product.Products
import org.jetbrains.exposed.sql.transactions.transaction

class ProductDao {
    fun getProduct(codeBar: String) = transaction {
        Product.find { Products.code eq codeBar }.firstOrNull()
    }
    fun newProduct(product: ProductJson): Product? {
        if(getProduct(product.code) != null) return null
        else {
            val points = getPoints(product.sugar,product.fat,product.saturatedFat,product.salt,product.palmOil,product.additives,product.vegan,product.vegetarian,product.celiaco)
            return transaction {
                Product.new {
                    code = product.code
                    productName = product.productName
                    brands = product.brands
                    price = product.price ?: 0.0
                    this.points = points
                    sugar = product.sugar
                    fat = product.fat
                    saturatedFat = product.saturatedFat
                    salt = product.salt
                    palmOil = product.palmOil
                    additives = product.additives
                    allergens = product.allergens
                    vegan = product.vegan
                    vegetarian = product.vegetarian
                    celiaco = product.celiaco
                    ecoscoreGrade = product.ecoscoreGrade
                    origins = product.origins
                    quantity = product.quantity
                    ingredientsText = product.ingredientsText
                    imageUrl = product.imageUrl
                    imageNutrimentsUrl = product.imageNutrimentsUrl
                    imageIngredientsUrl = product.imageIngredientsUrl
                }
            }
        }
    }

    private fun getPoints(sugar: Int, fat: Int, saturatedFat: Int, salt: Int, palmOil: Int, additives: Int, vegan: Boolean?, vegetarian: Boolean?, celiaco: Boolean?): Int {
        var pointsValue = 0
        celiaco?.let { if (celiaco) pointsValue += 3 }
        vegan?.let { if (vegan) pointsValue += 3 }
        vegetarian?.let { if (vegetarian) pointsValue += 3 }
        when(fat) {
            3 -> pointsValue += 24
            2 -> pointsValue += 16
            1 -> pointsValue -= 15
        }
        when(salt) {
            3 -> pointsValue += 22
            2 -> pointsValue += 18
            1 -> pointsValue -= 16
        }
        when(saturatedFat) {
            3 -> pointsValue += 26
            2 -> pointsValue += 15
            1 -> pointsValue -= 20
        }
        when(sugar) {
            3 -> pointsValue += 28
            2 -> pointsValue += 13
            1 -> pointsValue -= 22
        }
        when(palmOil) {
            2 -> pointsValue -= 10
            1 -> pointsValue -= 20
        }
        when(additives) {
            2 -> pointsValue -= 20
            1 -> pointsValue -= 30
        }
        if (pointsValue < 0) pointsValue = 0
        if (pointsValue > 100) pointsValue = 100
        return pointsValue
    }

    fun updatePrice(codeBar: String, price: Double) {
        val product = getProduct(codeBar)
        if (product != null) {
            transaction {
                product.price = price
            }
        }
    }

    fun updateProduct(product: ProductJson) = transaction {
        val points = getPoints(product.sugar,product.fat,product.saturatedFat,product.salt,product.palmOil,product.additives,product.vegan,product.vegetarian,product.celiaco)
        Product.find { Products.code eq product.code }.firstOrNull().apply {
            this!!
            productName = product.productName
            brands = product.brands
            this.points = points
            sugar = product.sugar
            fat = product.fat
            saturatedFat = product.saturatedFat
            salt = product.salt
            palmOil = product.palmOil
            additives = product.additives
            allergens = product.allergens
            vegan = product.vegan
            vegetarian = product.vegetarian
            celiaco = product.celiaco
            ecoscoreGrade = product.ecoscoreGrade
            origins = product.origins
            quantity = product.quantity
            ingredientsText = product.ingredientsText
        }
    }

    fun getUpdatedPoints(codeBar: String): Int {
        val fonded = getProduct(codeBar)
        return if (fonded == null) -1
        else fonded.points?:0
    }

    fun updateImage(imageUrl: String, codeBar: String): Boolean {
        val product = getProduct(codeBar)
        if (product == null) return false
        else transaction {
            product.imageUrl = imageUrl
        }
        return true
    }
}