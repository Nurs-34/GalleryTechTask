package kg.nambaone.gallerytechtask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.nambaone.gallerytechtask.model.PhotoModel
import kg.nambaone.gallerytechtask.repository.PhotoRepository
import kg.nambaone.gallerytechtask.utils.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PhotoListViewModel : ViewModel() {
    private val repository = PhotoRepository
    var photoList = ArrayList<PhotoModel>()

    init {
        viewModelScope.launch { loadPhotoList(Constants.page, Constants.perPage) }
    }

    suspend fun loadPhotoList(page: Int, perPage: Int) {
        val list = viewModelScope.async { repository.loadPhotoList(page, perPage).body()?.photos }
        photoList = list.await() as ArrayList<PhotoModel>
    }
}