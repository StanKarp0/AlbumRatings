package com.stankarp0.albumratings.ui.albumdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.*
import kotlinx.coroutines.*


class AlbumDetailsViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()
    private val _ratingObject = MutableLiveData<RatingObject>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    val ratingObject: LiveData<RatingObject>
        get() = _ratingObject

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Sets the value of the status LiveData to the Mars API status.
     */
    fun updateAlbumRatings(album: AlbumProperty) {
        coroutineScope.launch {
            val randomDeferred = RatingApi.retrofitService.album(album.albumId)

            try {
                val result = randomDeferred.await()
                _response.value ="Success: ${result.ratings.size} Rating retrieved"
                _ratingObject.value = result
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
            Log.i("AlbumDetailsViewModel", _response.value.toString())
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
