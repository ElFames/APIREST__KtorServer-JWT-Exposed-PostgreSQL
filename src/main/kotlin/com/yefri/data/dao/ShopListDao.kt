package com.yefri.data.dao

import com.yefri.data.models.relations.shopListProduct.ShopListProduct
import com.yefri.data.models.relations.shopListProduct.ShopListProducts
import com.yefri.data.models.relations.storeProduct.StoreProduct
import com.yefri.data.models.shopList.ShopList
import com.yefri.data.models.user.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class ShopListDao {
    fun updateUnds(user: User, codeBar: String, quantity: Int) {
        transaction {
            val shopList = user.shopList()
            val product = getProductByCode(codeBar, shopList!!)

            if (product!!.quantity <= 1 && quantity <= -1) return@transaction
            else {
                product.quantity = (product.quantity) + (quantity)
            }
        }
    }
    fun addProductByInfo(newProduct: com.yefri.data.models.product.Product, shopList: ShopList, unds: String) = transaction{
        ShopListProduct.new {
            this.shopList = shopList
            this.product = newProduct
            this.quantity = unds.toInt()
        }
    }
    fun deleteProduct(codeBar: String, user: User) {
        var product: com.yefri.data.models.product.Product?
        val shopList = user.shopList()
        transaction {
            getProductByCode(codeBar, shopList!!)
            product = com.yefri.data.models.product.Product.find { com.yefri.data.models.product.Products.code eq codeBar }.firstOrNull()
            ShopListProduct.find {
                ShopListProducts.productId eq product!!.id and
                        (ShopListProducts.shopListId eq shopList.id)
            }
                .firstOrNull()!!
                .delete()
        }
    }
    fun createShopList(newUser: User) = transaction {
        ShopList.new {
            this.shopListName = "Lista de la Purchase"
            this.user = newUser
        }
    }

    fun getProductByCode(codeBar: String, shopList: ShopList): ShopListProduct? {
        var prod: ShopListProduct? = null
        transaction {
            shopList.products.forEach {
                if(it.product.code == codeBar) {
                    prod = it
                    return@forEach
                }
            }
        }
        return prod
    }
    fun cloneProduct(newProduct: StoreProduct?, shopList: ShopList) {
        if (newProduct != null) {
            ShopListProduct.new {
                this.shopList = shopList
                this.product = newProduct.product
                this.quantity = newProduct.quantity
            }
        }
    }
    fun deleteAllProducts(): Boolean {
        return try {
            transaction {
                val allProducts = ShopList.all()
                allProducts.forEach {
                    it.delete()
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}