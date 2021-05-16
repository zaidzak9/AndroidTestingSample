package com.zaidzakir.androidtestingsample.shoppingListApp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *Created by Zaid Zakir
 */
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}