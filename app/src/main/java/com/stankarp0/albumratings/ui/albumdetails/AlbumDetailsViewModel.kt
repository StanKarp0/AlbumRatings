package com.stankarp0.albumratings.ui.albumdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.*
import kotlinx.coroutines.*


class AlbumDetailsViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _ratingObject = MutableLiveData<RatingObject>()

    // The external immutable LiveData for the response String
    val ratingObject: LiveData<RatingObject>
        get() = _ratingObject

    private val _performer = MutableLiveData<PerformerProperty>()
    val performer: LiveData<PerformerProperty>
        get() = _performer

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun updateAlbumRatings(album: AlbumProperty) {
        coroutineScope.launch {
            val randomDeferred = RatingApi.retrofitService.album(album.albumId)
            try {
                val result = randomDeferred.await()
                _ratingObject.value = result
            } catch (e: Exception) {
                Log.e("AlbumDetailsViewModel","Failure: ${e.message}")
            }
        }
    }

    fun findPerformer(album: AlbumProperty) {
        coroutineScope.launch {
            val performerDeferred = PerformerApi.retrofitService.performer(album.performerId)
            try {
                val result = performerDeferred.await()
                _performer.value = result
            } catch (e: Exception) {
                Log.e("AlbumDetailsViewModel","Failure: ${e.message}")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
