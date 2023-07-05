package kg.nambaone.gallerytechtask.ui

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.nambaone.gallerytechtask.R
import kg.nambaone.gallerytechtask.model.PhotoModel
import kg.nambaone.gallerytechtask.repository.PhotoRepository
import kg.nambaone.gallerytechtask.ui.dialogs.PhotoDetailDialog
import kg.nambaone.gallerytechtask.utils.Constants.Companion.fourColumnInRecyclerView
import kg.nambaone.gallerytechtask.utils.Constants.Companion.page
import kg.nambaone.gallerytechtask.utils.Constants.Companion.perPage
import kg.nambaone.gallerytechtask.utils.Constants.Companion.twoColumnInRecyclerView
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotoListViewModel(state: SavedStateHandle) : ViewModel() {
    private val savedStateHandle = state
    private val repository = PhotoRepository

    val photoListActionFlow: MutableSharedFlow<PhotoListAction> = MutableSharedFlow()

    private val _myLoadingStateFlow =
        savedStateHandle.get<MutableStateFlow<Boolean>>(LOADING_VALUE_KEY)
            ?: MutableStateFlow(true)
    val myLoadingStateFlow = _myLoadingStateFlow

    private val _photoListFlow =
        savedStateHandle.get<MutableStateFlow<List<PhotoModel>>>(PHOTO_LIST_VALUE_KEY)
            ?: MutableStateFlow(emptyList())
    val photoListFlow = _photoListFlow

    init {
        viewModelScope.launch {
            loadPhotoList(
                page = page,
                perPage = perPage
            )
        }
    }

    suspend fun loadPhotoList(page: Int, perPage: Int) {
        _myLoadingStateFlow.emit(false)

        try {
            val response = repository.loadPhotoList(page, perPage)

            if (response.isSuccessful) {
                val list =
                    viewModelScope.async { response.body()?.photos }
                val photoList = list.await() as List<PhotoModel>
                _photoListFlow.value = photoList

                _myLoadingStateFlow.emit(true)
            }
        } catch (e: Exception) {
            photoListActionFlow.emit(PhotoListAction.ShowInternetErrorToast)
            Log.e("PhotoListVM", "Exception: $e")
        }
    }

    fun calculateSpanCount(orientation: Int): Int =
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) fourColumnInRecyclerView
        else twoColumnInRecyclerView

    fun showPhotoDialog(activity: FragmentActivity, photoModel: PhotoModel) {
        val photoUrl = photoModel.photoUrl?.originalSize
        val dialog = PhotoDetailDialog.newInstance(photoUrl)
        dialog.show(
            activity.supportFragmentManager,
            "Photo detail dialog"
        )
    }

    fun getStringRes(context: Context): String = context.getString(R.string.no_description)

    fun onSaveInstanceState() {
        savedStateHandle[LOADING_VALUE_KEY] = _myLoadingStateFlow.value
        savedStateHandle[PHOTO_LIST_VALUE_KEY] = _photoListFlow.value
    }

    companion object {
        private const val LOADING_VALUE_KEY = "is loading"
        private const val PHOTO_LIST_VALUE_KEY = "photo list"
    }

    sealed class PhotoListAction {
        object ShowInternetErrorToast : PhotoListAction()
    }
}