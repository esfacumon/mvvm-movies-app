package com.example.wembleymovies.data.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wembleymovies.data.model.Movie
import com.example.wembleymovies.data.model.MovieRepository
// import com.example.wembleymovies.data.model.MovieRepository
import kotlinx.coroutines.launch

class SearchMoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {
//
//    private val _movies = MutableLiveData<List<Movie>>()
//    val movies: LiveData<List<Movie>>
//        get() = _movies
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean>
//        get() = _isLoading
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String>
//        get() = _error
//
//
//    // todo: test this
//    fun searchMovies(query: String) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val result = movieRepository.searchMovies(query)
//                _movies.value = result
//                _isLoading.value = false
//            } catch (exception: Exception) {
//                _error.value = "Error fetching movies: ${exception.message}"
//                _isLoading.value = false
//            }
//        }
//    }
//
//    // todo: test this
//    fun getPopularMovies() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                val result = movieRepository.getPopularMovies()
//                _movies.value = result
//                _isLoading.value = false
//            } catch (exception: Exception) {
//                _error.value = "Error fetching popular movies: ${exception.message}"
//                _isLoading.value = false
//            }
//        }
//    }
}
