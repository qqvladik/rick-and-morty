package by.mankevich.rickandmorty.library.db.base

interface BaseRepository {
    fun validateIds(ids: List<Int>): Boolean {
        return ids.isNotEmpty()
    }
}