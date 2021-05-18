package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.responses.ImageResponse
import com.zaidzakir.androidtestingsample.shoppingListApp.repositories.ShoppingRepository
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Events
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun validateShoppingItem(name: String, amountString: String, priceString: String){

    }

    fun searchImage(query:String) {

    }

}