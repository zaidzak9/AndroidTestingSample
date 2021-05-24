package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.responses.ImageResponse
import com.zaidzakir.androidtestingsample.shoppingListApp.repositories.ShoppingRepository
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Constants
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Events
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
): ViewModel() {

    val getShoppingItems = repository.observeAllShoppingItem()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Events<Resource<ImageResponse>>>()
    val image : LiveData<Events<Resource<ImageResponse>>> =_images

    private val _imagesUrl = MutableLiveData<String>()
    val imageUrl : LiveData<String> =_imagesUrl

    private val _insertShoppingItem = MutableLiveData<Events<Resource<ShoppingItem>>>()
    val insertShoppingItem : LiveData<Events<Resource<ShoppingItem>>> =_insertShoppingItem

    fun setImageUrl(imageUrl:String){
        _imagesUrl.postValue(imageUrl)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemInDB(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun validateShoppingItem(name: String, amount: String, price: String){
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()){
             _insertShoppingItem.postValue(Events(Resource.error("Please enter all information",null)))
            return
        }

        if (name.length > Constants.MAX_ITEM_NAME_LENGTH){
            _insertShoppingItem.postValue(Events(Resource.error("Length of item name should be less than ${Constants.MAX_ITEM_NAME_LENGTH}",null)))
            return
        }

        if (price.length > Constants.MAX_ITEM_PRICE_LENGTH){
            _insertShoppingItem.postValue(Events(Resource.error("Length of price  should be less than ${Constants.MAX_ITEM_PRICE_LENGTH}",null)))
            return
        }

        if (price.length > Constants.MAX_ITEM_PRICE_LENGTH){
            _insertShoppingItem.postValue(Events(Resource.error("Length of price  should be less than ${Constants.MAX_ITEM_PRICE_LENGTH}",null)))
            return
        }

        val amountString = try {
           amount.toInt()
        }catch (e:Exception){
            _insertShoppingItem.postValue(Events(Resource.error("Pleas enter valid amount",null)))
            return
        }

        val shoppingItem = ShoppingItem(name,amountString,price.toFloat(),_imagesUrl.value?:"")
        insertShoppingItemInDB(shoppingItem)
        setImageUrl("")
        _insertShoppingItem.postValue(Events(Resource.success(shoppingItem)))

    }

    fun searchImage(query:String) {
        if (query.isNullOrEmpty()){
            return
        }

        _images.value = Events(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(query)
            _images.value = Events(response)
        }
    }

}