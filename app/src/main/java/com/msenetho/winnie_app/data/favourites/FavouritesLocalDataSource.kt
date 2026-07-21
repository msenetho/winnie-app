package com.msenetho.winnie_app.data.favourites

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.favouritesDataStore by preferencesDataStore(name = "favourites")

private val FAVOURITES_IDS_KEY = stringSetPreferencesKey("favourite_ids")

class FavouritesLocalDataSource(
    private val context: Context
) {
    val favouriteIds: Flow<Set<String>> = context.favouritesDataStore.data
        .map { prefs ->
            prefs[FAVOURITES_IDS_KEY] ?: emptySet()
        }

    suspend fun toggle(id: String) {
        context.favouritesDataStore.edit { prefs ->
            val updated = (prefs[FAVOURITES_IDS_KEY] ?: emptySet()).toMutableSet()

            if (id in updated) {
                updated.remove(id)
            } else {
                updated.add(id)
            }

            prefs[FAVOURITES_IDS_KEY] = updated
        }
    }
}