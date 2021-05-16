package com.zaidzakir.androidtestingsample.shoppingListApp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *Created by Zaid Zakir
 */
@Entity(tableName = "shopping_items")
data class ShoppingItem (
    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)