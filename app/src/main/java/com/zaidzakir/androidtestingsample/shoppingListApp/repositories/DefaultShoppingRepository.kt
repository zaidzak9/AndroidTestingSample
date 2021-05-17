package com.zaidzakir.androidtestingsample.shoppingListApp.repositories

import androidx.lifecycle.LiveData
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingDao
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.PixabayAPI
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.responses.ImageResponse
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(query: String): Resource<ImageResponse> {
        return try {
           val response = pixabayAPI.searchImage(query)
            if (response.isSuccessful){
               response.body()?.let {imageResponse ->
                   return@let Resource.success(imageResponse)
               }?: Resource.error("An unknown error occurred",null)
            }else{
                Resource.error("An unknown error occurred",null)
            }
        }catch (e:Exception){
            return Resource.error("Something went wrong! $e",null)
        }
    }

}