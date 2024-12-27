package com.ferraz.playshow.domain.movies.usecase.mylist.getall

import com.ferraz.playshow.domain.movies.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MyList {
    operator fun invoke(): Flow<List<MovieItem>>
}
