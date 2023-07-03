package kg.nambaone.gallerytechtask.utils.network.response

import com.google.gson.annotations.SerializedName
import kg.nambaone.gallerytechtask.model.PhotoModel

data class PhotoResponse(

    @SerializedName("page") val page: Int?,
    @SerializedName("per_page") val perPage: Int?,
    @SerializedName("photos") val photos: List<PhotoModel?>,
    @SerializedName("next_page") val nextPage: String?
)