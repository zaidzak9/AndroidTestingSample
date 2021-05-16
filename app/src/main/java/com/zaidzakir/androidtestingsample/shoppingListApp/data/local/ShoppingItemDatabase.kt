package com.zaidzakir.androidtestingsample.shoppingListApp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *Created by Zaid Zakir
 */
@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}