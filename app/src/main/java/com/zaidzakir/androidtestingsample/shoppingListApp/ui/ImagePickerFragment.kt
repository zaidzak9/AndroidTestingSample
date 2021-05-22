package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zaidzakir.androidtestingsample.R

/**
 *Created by Zaid Zakir
 */
class ImagePickerFragment:Fragment(R.layout.fragment_image_pick) {
    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
    }
}