package com.example.recipemaker.data.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipemaker.domain.interfaces.DataStoreRepository
import com.example.recipemaker.utils.Constants.USER_PREFERECE_NAME
import kotlinx.coroutines.flow.first
import javax.inject.Inject



private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(USER_PREFERECE_NAME)

class DataStoreRepositoryIml @Inject constructor(
    private val context: Context
): DataStoreRepository {

    //guardar String (usado para guardar el correo del usuario logeado)
    override suspend fun putString(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit{
            it[preferenceKey] = value
        }
    }
    //guardar Boolean (usado para guardar el estado de si esta logeado o no)
    override suspend fun putBoolean(key: String, value: Boolean) {
        val preferenceKey = booleanPreferencesKey(key)
        context.dataStore.edit{
            it[preferenceKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try{
            val _preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[_preferencesKey]
        } catch(e: Exception){
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return try {
            val _preferencesKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[_preferencesKey]
        } catch(e: Exception){
            e.printStackTrace()
            null
        }
    }

}