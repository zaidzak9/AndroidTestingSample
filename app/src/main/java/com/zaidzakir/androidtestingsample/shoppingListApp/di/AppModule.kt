package com.zaidzakir.androidtestingsample.shoppingListApp.di

import android.content.Context
import androidx.room.Room
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingDao
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItemDatabase
import com.zaidzakir.androidtestingsample.shoppingListApp.data.remote.PixabayAPI
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Constants.BASE_URL
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *Created by Zaid Zakir
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSearchImageApi():PixabayAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,ShoppingItemDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database:ShoppingItemDatabase
    ) = database.shoppingDao()
}