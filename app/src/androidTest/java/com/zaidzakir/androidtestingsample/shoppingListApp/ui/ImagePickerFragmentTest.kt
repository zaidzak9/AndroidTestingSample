package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.androidtestingsample.R
import com.zaidzakir.androidtestingsample.data.remote.MockShoppingRepositoryAndroidTest
import com.zaidzakir.androidtestingsample.getOrAwaitValue
import com.zaidzakir.androidtestingsample.launchFragmentInHiltContainer
import com.zaidzakir.androidtestingsample.shoppingListApp.adapters.ImageAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

/**
 * Created by Zaid Zakir
 */
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickerFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun `clickImage_popBackStack`(){
        val navController = mock(NavController::class.java)
        val imageUrl = "TESTURL"
        val testViewModel = ShoppingViewModel(MockShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<ImagePickerFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(),navController)
            imageAdapter.images = listOf(imageUrl)
            shoppingViewModel = testViewModel
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.imageUrl.getOrAwaitValue()).isEqualTo(imageUrl)
    }

}


