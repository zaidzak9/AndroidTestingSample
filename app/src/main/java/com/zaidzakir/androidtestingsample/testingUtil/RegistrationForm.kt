package com.zaidzakir.androidtestingsample.testingUtil

/**
 *Created by Zaid Zakir
 * -- rules
 * -- Valid Username Password
 * -- Username should not be empty
 * -- Password in both fields should match
 * -- Username should not be taken
 * -- Password should be greater than or equal to 8
 */
object RegistrationForm {

    private val existingUser = listOf("Zaid","Zakir")

    fun registerUser(
        userName:String,
        password:String,
        confirmPassword:String
    ):Boolean{
        if (existingUser.contains(userName) || userName.isNullOrEmpty()){
            return false
        }
        if (password.length < 8){
            return false
        }
        if (!password.contentEquals(confirmPassword)){
            return false
        }
        if (!existingUser.contains(userName) && password.contentEquals(confirmPassword) && password.length>7){
            return true
        }
        return true
    }
}