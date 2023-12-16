package com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.di

import com.bangkit23dwinovirhmwt.submissionakhirjetpackcompose.data.AnimeGhibliRepository

object Injection {
    fun provideRepository(): AnimeGhibliRepository {
        return AnimeGhibliRepository.getInstance()
    }
}