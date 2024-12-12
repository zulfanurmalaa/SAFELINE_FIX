package com.capstone.fall_guard.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class AccountPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getImageProfile() = dataStore.data.map { it[IMAGE_PREFERENCES] ?: preferencesDefaultValue }
    fun getUsername() = dataStore.data.map { it[USERNAME_PREFERENCES] ?: preferencesDefaultValue }
    fun getTelpNumber() = dataStore.data.map { it[TELP_PREFERENCES] ?: preferencesDefaultValue }

    suspend fun saveImage(
        image: String
    ) {
        dataStore.edit { prefs ->
            prefs[IMAGE_PREFERENCES] = image
        }
    }

    suspend fun saveUsername(
        username: String
    ) {
        dataStore.edit { prefs ->
            prefs[USERNAME_PREFERENCES] = username
        }
    }

    suspend fun saveTelp(
        telp: String
    ) {
        dataStore.edit { prefs ->
            prefs[TELP_PREFERENCES] = telp
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "account_preferences")

        private val IMAGE_PREFERENCES = stringPreferencesKey("image_preferences")
        private val USERNAME_PREFERENCES = stringPreferencesKey("username_preferences")
        private val TELP_PREFERENCES = stringPreferencesKey("telp_preferences")

        const val preferencesDefaultValue: String = "preferences_default_value"

        @Volatile
        private var INSTANCE: AccountPreferences? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            val instance = AccountPreferences(context.dataStore)
            INSTANCE = instance
            instance
        }
    }
}