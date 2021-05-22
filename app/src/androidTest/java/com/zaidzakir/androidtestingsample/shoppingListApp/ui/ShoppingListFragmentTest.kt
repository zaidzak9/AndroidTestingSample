package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.zaidzakir.androidtestingsample.R
import com.zaidzakir.androidtestingsample.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by Zaid Zakir
 */
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingListFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun `clickAddItemBtn_NavigateToAddShoppingFrag`(){
        val findVanController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingListFragment> {
            Navigation.setViewNavController(requireView(),findVanController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(findVanController).navigate(
            ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingFragment()
        )
    }


}