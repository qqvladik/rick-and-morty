package by.mankevich.rickandmorty.library.base

import by.mankevich.rickandmorty.domain.base.IEntity

interface BaseRepository <K: IEntity> {
    fun validateIds(ids: List<Int>): Boolean {
        return ids.isNotEmpty()
    }

    suspend fun fetchAllByIsConnect(limit: Int, page: Int): List<K>
}