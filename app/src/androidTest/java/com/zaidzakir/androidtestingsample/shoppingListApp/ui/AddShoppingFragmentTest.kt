package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.zaidzakir.androidtestingsample.R
import com.zaidzakir.androidtestingsample.data.remote.MockShoppingRepositoryAndroidTest
import com.zaidzakir.androidtestingsample.getOrAwaitValue
import com.zaidzakir.androidtestingsample.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.androidtestingsample.shoppingListApp.data.local.ShoppingItem
import javax.inject.Inject

/**
 * Created by Zaid Zakir
 */
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingFragmentTest{
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactoryAndroidTest

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }

    @Test
    fun clickInsertIntoDb_itemInsertedIntoDb() {
        val testViewModel = ShoppingViewModel(MockShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingFragment>(
            fragmentFactory = fragmentFactory
        ) {
            shoppingViewModel = testViewModel
        }
        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.getShoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
    }

    @Test
    fun pressBackButton_imageUrlSetToEmptyString(){
        val testViewModel = ShoppingViewModel(MockShoppingRepositoryAndroidTest())
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingFragment> (
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            testViewModel.setImageUrl("http://url")
            shoppingViewModel = testViewModel
        }
        pressBack()
        val value = testViewModel.imageUrl.getOrAwaitValue()
        assertThat(value).isEmpty()
    }


    @Test
    fun clickAddImageBtn_navigateToImagePicker(){
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingFragment> (
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(),navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingFragmentDirections.actionAddShoppingFragmentToImagePickerFragment()
        )
    }


    @Test
    fun pressBackButton_popBackStack(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingFragment> (
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(),navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }



}