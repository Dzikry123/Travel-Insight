package com.tubes.travel_insight.preferences


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "auth_token")

class AuthPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val AUTH_TOKEN_KEY = stringPreferencesKey("token")

    val getAuthToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[AUTH_TOKEN_KEY] ?: ""
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { auth ->
            auth[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = ""
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AuthPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}

