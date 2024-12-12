package com.capstone.fall_guard.data.repo

import android.content.Context
import com.capstone.fall_guard.data.local.AccountPreferences
import com.capstone.fall_guard.data.networks.FirebaseHelper

class MainRepository private constructor(
    private val accountPreferences: AccountPreferences,
    private val firebaseHelper: FirebaseHelper
) {
    /***
     * Firebase Utils
     */

    fun observeUserActivity() = firebaseHelper.observeUserActivity()
    fun observeUserFallHistory() = firebaseHelper.observeUserFallHistory()


    /***
     * Account Preferences Utils
     */

    fun getImageProfile() = accountPreferences.getImageProfile()
    fun getUsername() = accountPreferences.getUsername()
    fun getTelpNumber() = accountPreferences.getTelpNumber()

    suspend fun saveImage(image: String) = accountPreferences.saveImage(image)
    suspend fun saveUsername(username: String) = accountPreferences.saveUsername(username)
    suspend fun saveTelp(telp: String) = accountPreferences.saveTelp(telp)

    suspend fun clearPreferences() = accountPreferences.clearPreferences()

    companion object {
        fun getInstance(context: Context) = MainRepository(
            accountPreferences = AccountPreferences.getInstance(context),
            firebaseHelper = FirebaseHelper.getInstance()
        )
    }
}