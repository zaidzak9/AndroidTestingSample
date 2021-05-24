package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaidzakir.androidtestingsample.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}