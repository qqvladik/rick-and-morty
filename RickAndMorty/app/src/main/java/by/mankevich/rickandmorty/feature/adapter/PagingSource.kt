package by.mankevich.rickandmorty.feature.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.mankevich.rickandmorty.domain.base.IEntity
import by.mankevich.rickandmorty.library.base.BaseFilter
import by.mankevich.rickandmorty.library.base.BaseRepository
import by.mankevich.rickandmorty.library.repository.filter.FilterCharacters

class PagingSource <K: IEntity> (
    private val repository: BaseRepository<K>, //todo add filter
    private val search: String
): PagingSource<Int, K>() {
    override fun getRefreshKey(state: PagingState<Int, K>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, K> {
        val page = params.key ?: 1
        return try {
            val entities = repository.fetchAllByIsConnect(params.loadSize, page, search)
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