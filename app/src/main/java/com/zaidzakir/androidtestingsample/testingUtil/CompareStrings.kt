package com.zaidzakir.androidtestingsample.testingUtil

import android.content.Context

/**
 *Created by Zaid Zakir
 * --rules
 * --compare string from resource id
 * --if equal to parameter string return true
 * -- else return false
 */
class CompareStrings {

    fun compareStrings(context:Context,
    resourceString:Int,
    string:String):Boolean {
        if (context.getString(resourceString).contentEquals(string)) {
            return true
        }
        return false
    }
}