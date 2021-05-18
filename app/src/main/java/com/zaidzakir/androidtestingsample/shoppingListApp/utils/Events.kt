package com.zaidzakir.androidtestingsample.shoppingListApp.utils

/**
 *Created by Zaid Zakir
 * This class is made to track if data has already been emitted from the view model to its fragments
 * its used to track if for example : if error occurred while in landscape and user rotates device
 * we don't want data to be emitted again
 */
open class Events<out T>(private val content:T) {

    var hasDataBeenHandled:Boolean = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun contentIfHandled():T?{
        return if (hasDataBeenHandled){
            null
        }else{
            hasDataBeenHandled = true
            return content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}