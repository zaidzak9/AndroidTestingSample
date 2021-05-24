package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.zaidzakir.androidtestingsample.R
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class AddShoppingFragment @Inject constructor(
    val glide: RequestManager
):Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObserver()
        btnAddShoppingItem.setOnClickListener {
            shoppingViewModel.validateShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString()
            )
        }
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

    private fun subscribeToObserver(){
        shoppingViewModel.imageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(ivShoppingImage)
        })
        shoppingViewModel.insertShoppingItem.observe(viewLifecycleOwner, Observer {
            it.contentIfHandled()?.let { result->
                when(result.status){
                    Status.SUCCESS -> {
                        Snackbar.make(
                            activity!!.findViewById(android.R.id.content),
                        "Shopping item Added",
                        Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                    Status.LOADING ->{
                        // NO OP
                    }
                    Status.ERROR ->{
                        Snackbar.make(activity!!.findViewById(android.R.id.content),
                            result.message?: "A Error occurred",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}