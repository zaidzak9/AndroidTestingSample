package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zaidzakir.androidtestingsample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shopping.*

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class ShoppingListFragment:Fragment(R.layout.fragment_shopping) {
    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingFragment())
        }
    }
}