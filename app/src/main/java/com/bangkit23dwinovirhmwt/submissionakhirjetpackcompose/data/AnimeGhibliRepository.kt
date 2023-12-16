package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.data

import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model.AnimeGhibli
import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.model.AnimeGhibliData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AnimeGhibliRepository {
    private val dummyAnimeGhibli = mutableListOf<AnimeGhibli>()

    init {
        if (dummyAnimeGhibli.isEmpty()) {
            AnimeGhibliData.dummyAnimeGhibli.forEach {
                dummyAnimeGhibli.add(it)
            }
        }
    }

    fun getAnimeGhibliById(animeGhibliId: Int): AnimeGhibli {
        return dummyAnimeGhibli.first {
            it.id == animeGhibliId
        }
    }

    fun getFavoriteAnimeGhibli(): Flow<List<AnimeGhibli>> {
        return flowOf(dummyAnimeGhibli.filter { it.isFavorite })
    }

    fun searchAnimeGhibli(query: String) = flow {
        val data = dummyAnimeGhibli.filter {
            it.title.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateAnimeGhibli(animeGhibliId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyAnimeGhibli.indexOfFirst { it.id == animeGhibliId }
        val result = if (index >= 0) {
            val animeGhibli = dummyAnimeGhibli[index]
            dummyAnimeGhibli[index] = animeGhibli.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: AnimeGhibliRepository? = null

        fun getInstance(): AnimeGhibliRepository =
            instance ?: synchronized(this) {
                AnimeGhibliRepository().apply {
                    instance = this
                }
            }
    }
}