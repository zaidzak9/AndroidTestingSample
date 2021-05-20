package com.zaidzakir.androidtestingsample.shoppingListApp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.responses.ImageResponse
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Resource

/**
 *Created by Zaid Zakir
 * used to simulate the behaviour of the real repository
 * will be passed to the testing view model
 */
class MockShoppingRepository : ShoppingRepository{
    //creating a list of shopping items to be inserted instead of a DB
    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(query: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}