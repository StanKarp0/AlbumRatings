package com.stankarp0.albumratings.ui.ratingdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RatingDetailsViewModel : ViewModel() {

    private val _performer = MutableLiveData<PerformerProperty>()
    val performer: LiveData<PerformerProperty>
        get() = _performer

    private val _album = MutableLiveData<AlbumProperty>()
    val album: LiveData<AlbumProperty>
        get() = _album

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun findPerformer(rating: RatingProperty) {
        coroutineScope.launch {
            val performerDeferred = RandomApi.retrofitService.performer(rating.performerId)
            try {
                val result = performerDeferred.await()
                _performer.value = result
            } catch (e: Exception) {
                Log.e("RatingDetailsViewModel","Failure: ${e.message}")
            }
        }
    }

    fun findAlbum(rating: RatingProperty) {
        coroutineScope.launch {
            val performerDeferred = RandomApi.retrofitService.album(rating.albumId)
            try {
                val result = performerDeferred.await()
                _album.value = result
            } catch (e: Exception) {
                Log.e("RatingDetailsViewModel","Failure: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}