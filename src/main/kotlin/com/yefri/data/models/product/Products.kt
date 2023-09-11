package com.yefri.data.models.product

import org.jetbrains.exposed.dao.id.IntIdTable

object Products: IntIdTable() {
    var code = varchar("code",500).uniqueIndex()
    var productName = varchar("productName",500).nullable()
    var brands = varchar("brands",500).nullable()
    var price = double("price").nullable()
    var points = integer("points").nullable().default(0)
    var sugar = integer("sugar").nullable().default(0)
    var fat = integer("fat").nullable().default(0)
    var saturatedFat = integer("saturatedFat").nullable().default(0)
    var salt = integer("salt").nullable().default(0)
    var palmOil = integer("palmOil").nullable().default(0)
    var additives = integer("additives").nullable().default(0)
    var allergens = varchar("allergens",500).nullable()
    var vegan = bool("vegan").nullable()
    var vegetarian = bool("vegetarian").nullable()
    var celiaco = bool("celiaco").nullable()
    var ecoscoreGrade = varchar("ecoscoreGrade",500).nullable()
    var origins = varchar("origins",500).nullable()
    var quantity = varchar("quantity",500).nullable()
    var ingredientsText = varchar("ingredientsText",5000).nullable()
    var imageUrl = varchar("imageUrl",500).nullable()
    var imageNutrimentsUrl = varchar("imageNutrimentsUrl",500).nullable()
    var imageIngredientsUrl = varchar("imageIngredientsUrl",5000).nullable()
}