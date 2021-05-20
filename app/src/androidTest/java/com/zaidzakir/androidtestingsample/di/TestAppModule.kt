package com.zaidzakir.androidtestingsample.di

import android.content.Context
import androidx.room.Room
import com.zaidzakir.androidtestingsample.HiltTestRunner
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

/**
 *Created by Zaid Zakir
 */
@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun inMemoryDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context,ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}