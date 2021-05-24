package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.zaidzakir.androidtestingsample.data.remote.MockShoppingRepositoryAndroidTest
import com.zaidzakir.androidtestingsample.shoppingListApp.adapters.ImageAdapter
import com.zaidzakir.androidtestingsample.shoppingListApp.adapters.ShoppingItemAdapter
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
class ShoppingFragmentFactoryAndroidTest @Inject constructor(
    private val adapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImagePickerFragment::class.java.name -> ImagePickerFragment(adapter)
            AddShoppingFragment::class.java.name -> AddShoppingFragment(glide)
            ShoppingListFragment::class.java.name -> ShoppingListFragment(
                shoppingItemAdapter,
                ShoppingViewModel(MockShoppingRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }

    }
}