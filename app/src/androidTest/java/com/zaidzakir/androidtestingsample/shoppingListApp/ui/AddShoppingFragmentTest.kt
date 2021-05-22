package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.zaidzakir.androidtestingsample.R
import com.zaidzakir.androidtestingsample.data.remote.MockShoppingRepository
import com.zaidzakir.androidtestingsample.getOrAwaitValue
import com.zaidzakir.androidtestingsample.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import com.google.common.truth.Truth.assertThat

/**
 * Created by Zaid Zakir
 */
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingFragmentTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltAndroidRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun pressBackButton_imageUrlSetToEmptyString(){
        val testViewModel = ShoppingViewModel(MockShoppingRepository())
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingFragment> {
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

        launchFragmentInHiltContainer<AddShoppingFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(ViewActions.click())

        verify(navController).navigate(
            AddShoppingFragmentDirections.actionAddShoppingFragmentToImagePickerFragment()
        )
    }

}