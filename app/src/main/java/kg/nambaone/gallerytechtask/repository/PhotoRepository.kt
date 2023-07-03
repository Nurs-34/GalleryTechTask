package kg.nambaone.gallerytechtask.repository

import kg.nambaone.gallerytechtask.utils.network.RestApiInterface
import kg.nambaone.gallerytechtask.utils.network.response.PhotoResponse
import retrofit2.Response

object PhotoRepository {
    private val apiService: RestApiInterface
        get() = RestApiInterface.invoke()

    suspend fun loadPhotoList(page: Int, perPage: Int): Response<PhotoResponse> {
        return apiService.getPhotoList(page, perPage)
    }
}