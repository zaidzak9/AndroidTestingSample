package com.zaidzakir.androidtestingsample.testingUtil

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.zaidzakir.androidtestingsample.R
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by Zaid Zakir
 */
class CompareStringsTest{
    private lateinit var compareStrings:CompareStrings

    @Before
    fun setup(){
        compareStrings = CompareStrings()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun ifBothStringMatches_returnTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = compareStrings.compareStrings(context, R.string.app_name,"AndroidTestingSample")
        assertThat(result).isTrue()
    }

    @Test
    fun ifBothStringDontMatches_returnFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = compareStrings.compareStrings(context, R.string.app_name,"3rdPartyApp")
        assertThat(result).isFalse()
    }
}