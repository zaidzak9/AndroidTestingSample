package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zaidzakir.androidtestingsample.R

/**
 *Created by Zaid Zakir
 */
class AddShoppingFragment:Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }
}