package com.sonu.imagesearchapp.ui.gallery

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.sonu.imagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: UnsplashRepository,
) : ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    /*
      we have to cache the data in viewModel because  when we rotate a device
      we can't load from same paging source.
     */
    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchedResults(queryString)
        //.cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }
}