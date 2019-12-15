package com.stankarp0.albumratings.ui.ratinglist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stankarp0.albumratings.services.RandomApi
import com.stankarp0.albumratings.services.RatingEmbedded
import com.stankarp0.albumratings.services.RatingObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RatingListViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _ratingObject = MutableLiveData<RatingObject>()

    // The external immutable LiveData for the response String
    val ratingObject: LiveData<RatingObject>
        get() = _ratingObject

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var page = 0

    init {
        updateRatings()
    }

    fun updateRatings() {
        coroutineScope.launch {
            val randomDeferred = RandomApi.retrofitService.allRatings(page)
            page += 1

            try {
                val result = randomDeferred.await()
                if (_ratingObject.value == null) {
                    _ratingObject.value = result
                } else if (result.ratings.isNotEmpty()){
                    val ratings = (_ratingObject.value?.ratings ?: listOf()) + result.ratings
                    _ratingObject.value = RatingObject(RatingEmbedded(ratings))
                }
            } catch (e: Exception) {
                Log.e("RatingListViewModel","Failure: ${e.message}")
            }
        }
    }

    fun reloadRatings() {
        page = 0
        _ratingObject.value = null
        updateRatings()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}