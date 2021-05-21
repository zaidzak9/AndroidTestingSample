package com.zaidzakir.androidtestingsample

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.testing.R
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 *Created by Zaid Zakir
 * A utility function for dagger hilt
 */

@ExperimentalCoroutinesApi
/**inline is a lambda function made more efficient, it will take its purpose and be injected into the use
 *generic bcoz we want to use it to all kinds of fragments
 *reified will be known at compile time
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs:Bundle? = null,
    themeResId :Int = R.style.FragmentScenarioEmptyFragmentActivityTheme,
    //attach fragment factory so it allows us to use constructor injection in our fragments
    fragmentFactory: FragmentFactory? = null,
    //lambda function to get reference to the fragment we jst launched
    crossinline action: T.() -> Unit = {}
){
    //create intent of activity , in which we want to attach out fragment
    val mainActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestingActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId)

    //create activity scenario that will attach the fragment to the activity
    ActivityScenario.launch<HiltTestingActivity>(mainActivityIntent).onActivity {activity->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        //this will create our fragment
        val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs

        activity.supportFragmentManager.beginTransaction()
            .add(android.R.id.content,fragment,"")
            .commitNow()
        (fragment as T).action()
    }
}
