package com.msenetho.winnie_app.data.favourites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouritesRepository(
    private val localDataSource: FavouritesLocalDataSource
) {
    val favouriteIds: Flow<Set<Int>> = localDataSource.favouriteIds
        .map { stringIds ->
            stringIds
                .mapNotNull { it.toIntOrNull() }
                .toSet()
            // converts setOf() elements from string to int
            // .mapNotNull so bad values get skipped
        }

    suspend fun toggle(id: Int) {
        localDataSource.toggle(id.toString())
    }
}