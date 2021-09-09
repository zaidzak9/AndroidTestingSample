package com.zaidzakir.androidtestingsample

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 *Created by Zaid Zakir
 * The reason why we need this class is our ShoppingViewModelTest failed , if you look at our ShoppingViewModel class
 * we use a coroutine inside our fun validateShoppingItem(name: String, amount: String, price: String){} function,
 * and this calls our insertItemsIntoDb() function and this uses a suspend function, and that uses the MainDispatcher
 * which relies on the Mainlooper which is only availble in a real app scenario
 * Since we run this test in our JVM we dont have access to a real apps enviroment like in androidTest and access to MainDispatcher.
 * So to solve this we need to create our own JUnit Rule.
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}