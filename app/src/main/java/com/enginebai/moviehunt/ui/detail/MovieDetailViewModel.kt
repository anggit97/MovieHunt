package com.enginebai.moviehunt.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.enginebai.base.view.BaseViewModel
import com.enginebai.moviehunt.data.local.*
import com.enginebai.moviehunt.data.repo.MovieRepo
import io.reactivex.schedulers.Schedulers
import org.koin.core.inject

class MovieDetailViewModel : BaseViewModel() {
    private val movieRepo: MovieRepo by inject()
    private val _movieDetail = MutableLiveData<MovieModel>()

    val posterUrl: LiveData<String> = Transformations.map(_movieDetail) { it.getPosterUrl() }
    val title: LiveData<String> = Transformations.map(_movieDetail) { it.displayTitle() }
    val rating: LiveData<Float> = Transformations.map(_movieDetail) { it.display5StarsRating() }
    val voteCount: LiveData<String> = Transformations.map(_movieDetail) { it.displayVoteCount() }
    val duration: LiveData<String> = Transformations.map(_movieDetail) { it.displayDuration() }
    val releaseDate: LiveData<String> =
        Transformations.map(_movieDetail) { it.displayReleaseDate() }
    val overview: LiveData<String> = Transformations.map(_movieDetail) { it.displayOverview() }

    fun fetchMovieDetail(id: String) {
        movieRepo.fetchMovieDetail(id)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { _movieDetail.postValue(it) }
            .subscribe()
            .disposeOnCleared()
    }
}