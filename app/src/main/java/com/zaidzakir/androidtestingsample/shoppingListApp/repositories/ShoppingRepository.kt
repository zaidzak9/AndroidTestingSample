package com.zaidzakir.androidtestingsample.shoppingListApp.repositories

import androidx.lifecycle.LiveData
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.responses.ImageResponse
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Resource

/**
 *Created by Zaid Zakir
 */
interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(query:String):Resource<ImageResponse>
}