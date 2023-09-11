package com.yefri.data.dao

import com.yefri.data.models.product.Product
import com.yefri.data.models.product.Products
import com.yefri.data.models.relations.shopListProduct.ShopListProduct
import com.yefri.data.models.relations.storeProduct.StoreProduct
import com.yefri.data.models.relations.storeProduct.StoreProducts
import com.yefri.data.models.relations.userStores.UserStores
import com.yefri.data.models.relations.userStores.UsersStores
import com.yefri.data.models.store.Store
import com.yefri.data.models.user.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class StoreDao {
    fun deleteProduct(codeBar: String, user: User) {
        var product: Product?
        val store = user.store()
        transaction {
            getProductByCode(codeBar, store)
            product = Product.find { Products.code eq codeBar }.firstOrNull()
            StoreProduct.find {
                StoreProducts.productId eq product!!.id and
                        (StoreProducts.storeId eq store.id)
            }
                .firstOrNull()!!
                .delete()
        }
    }
    fun cloneProduct(newProduct: ShopListProduct?, store: Store) = transaction{
        StoreProduct.new {
            this.store = store
            this.product = newProduct!!.product
            this.quantity = newProduct.quantity
        }
    }

    fun updateUnds(user: User, codeBar: String, quantity: Int) {
        transaction {
            val store = user.store()
            val product = getProductByCode(codeBar, store)
            if (product!!.quantity == 1 && quantity == -1) return@transaction
            else {
                product.quantity = (product.quantity) + (quantity)
            }
        }
    }

    fun createStore(newUser: User) = transaction {
        Store.new {
            this.storeName = "Tu Hogar"
            this.user = newUser
        }
    }
    fun addProduct(product: Product?, store: Store) = transaction {
        StoreProduct.new {
            this.store = store
            this.product = product!!
            this.quantity = 1
        }
    }
    fun getProductByCode(codeBar: String, store: Store): StoreProduct? {
        var prod: StoreProduct? = null
        transaction {
            store.products.forEach {
                if(it.product.code == codeBar) {
                    prod = it
                    return@forEach
                }
            }
        }
        return prod
    }
    fun exitCurrentStore(user: User): Boolean {
        val secondaryStore = transaction {
            UserStores.find { UsersStores.userId eq user.id }.firstOrNull()
        }
        return if (secondaryStore == null) false
        else {
            deleteSecondaryStore(user)
            true
        }
    }
    private fun deleteSecondaryStore(user: User) = transaction {
        UserStores.find { UsersStores.userId eq user.id }.firstOrNull()?.delete()
    }

    fun deleteAllProducts(): Boolean {
        return try {
            transaction {
                val allProducts = Store.all()
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