package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zaidzakir.androidtestingsample.R
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import kotlinx.android.synthetic.main.fragment_shopping.*

/**
 *Created by Zaid Zakir
 */
class AddShoppingFragment:Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingFragmentDirections.actionAddShoppingFragmentToImagePickerFragment()
            )
        }

        val callback = object : OnBackPressedCallback (true){
            override fun handleOnBackPressed() {
                shoppingViewModel.setImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}