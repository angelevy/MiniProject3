package com.angellevyne0045.miniproject3.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.angellevyne0045.miniproject3.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = "user_preference"
)

class UserDataStore (private  val context: Context){

    companion object {
        private val USER_NAME = stringPreferencesKey("title")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_PHOTO = stringPreferencesKey("coverUrl")
    }

    val userFlow: Flow<User> = context.dataStore.data.map { preferences ->
        User(
            title = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            coverUrl = preferences[USER_PHOTO] ?: "",
        )
    }

    suspend fun saveData(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = user.title
            preferences[USER_EMAIL] = user.email
            preferences[USER_PHOTO] = user.coverUrl
        }
    }
}