package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.zaidzakir.androidtestingsample.shoppingListApp.adapters.ImageAdapter
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class ShoppingFragmentFactory @Inject constructor(
    private val adapter :ImageAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickerFragment::class.java.name->ImagePickerFragment(adapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}