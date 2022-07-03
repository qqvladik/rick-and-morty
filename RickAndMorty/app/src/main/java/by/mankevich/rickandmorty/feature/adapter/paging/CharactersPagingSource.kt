package by.mankevich.rickandmorty.feature.adapter.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.mankevich.rickandmorty.library.db.entity.CharacterEntity
import by.mankevich.rickandmorty.library.repository.CharactersRepository

class CharactersPagingSource(
    private val charactersRepository: CharactersRepository, //todo add filter
//    val query: String
): PagingSource<Int, CharacterEntity>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val page = params.key ?: 1
        return try {
            val entities = charactersRepository.fetchAllByIsConnect(params.loadSize, page)
            return LoadResult.Page(
                data = entities,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}