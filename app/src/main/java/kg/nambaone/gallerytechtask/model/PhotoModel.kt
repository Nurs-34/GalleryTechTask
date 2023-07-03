package kg.nambaone.gallerytechtask.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("photographer") val name: String?,
    @SerializedName("url") val photoUrl: String?,
    @SerializedName("alt") val photoDescription: String?
)