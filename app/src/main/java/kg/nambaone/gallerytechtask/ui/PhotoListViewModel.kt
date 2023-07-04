package kg.nambaone.gallerytechtask.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kg.nambaone.gallerytechtask.model.PhotoModel
import kg.nambaone.gallerytechtask.repository.PhotoRepository
import kg.nambaone.gallerytechtask.utils.Constants.Companion.page
import kg.nambaone.gallerytechtask.utils.Constants.Companion.perPage
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel : ViewModel() {
    private val repository = PhotoRepository
    private val _myLoadingStateFlow = MutableStateFlow(false)
    val myLoadingStateFlow = _myLoadingStateFlow.asStateFlow()
    var photoList = ArrayList<PhotoModel>()

    init {
        viewModelScope.launch {
            loadPhotoList(
                page = page,
                perPage = perPage
            )
        }
    }

    suspend fun loadPhotoList(page: Int, perPage: Int) {
        _myLoadingStateFlow.value = false

        try {
            val response = repository.loadPhotoList(page, perPage)

            if (response.isSuccessful) {
                val list =
                    viewModelScope.async { response.body()?.photos }
                photoList = list.await() as ArrayList<PhotoModel>
                Log.e("AAAAA", photoList.size.toString())

                _myLoadingStateFlow.value = true
            }
        } catch (e: Exception) {

        }


    }
}