package com.zaidzakir.androidtestingsample.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.androidtestingsample.getOrAwaitValue
import com.zaidzakir.androidtestingsample.launchFragmentInHiltContainer
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingDao
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItemDatabase
import com.zaidzakir.androidtestingsample.shoppingListApp.ui.ShoppingFragmentFactory
import com.zaidzakir.androidtestingsample.shoppingListApp.ui.ShoppingListFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

/**
 *Created by Zaid Zakir
 */
@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    //This rule tells android test to run everything one by one , without this u will get a error saying cannot complete task
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Inject
    @Named("test_db") //need named annotation to tell hilt to where to inject from
    lateinit var database:ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup(){
        //we use this to inject db reference
        hiltRule.inject()
        /*
        Im keeping the unused comment below as reference to show that we dont need to instatiate the db if we use hilt for testing as well
        below way isnt good if project too big, its okay for sample project like this but not really
         */
        /**
        database = Room.inMemoryDatabaseBuilder( //inMemoryDatabaseBuilder is used to tell the app and room to store data in the RAM
            // rather than persistemce memory so that we can have a new database for each testcases
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build() //we use this bcoz testcases should run in single thread ,
        // if they run in background or multiple threads , threads can manipulate each other, we want complete independence
        */
        dao = database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("itemTest",10,1F,"url",1)
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("itemTest",10,1F,"url",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

    @Test
    fun observeAllShoppingItem() = runBlockingTest{
        val shoppingItem = ShoppingItem("itemTest",10,1F,"url",1)
        dao.insertShoppingItem(shoppingItem)
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).hasSize(1)
    }

    @Test
    fun observeTotalPrice() = runBlockingTest{
        val shoppingItem1 = ShoppingItem("itemTest1",10,2F,"url",1)
        val shoppingItem2 = ShoppingItem("itemTest2",10,2F,"url",2)
        val shoppingItem3 = ShoppingItem("itemTest3",10,2F,"url",3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(10*2+10*2+10*2)
    }

//     @Test
//     fun testLaunchFragmentInHiltContainer(){
//         launchFragmentInHiltContainer<ShoppingListFragment>(
//             fragmentFactory = fragmentFactory
//         ) {
//         }
//     }


}