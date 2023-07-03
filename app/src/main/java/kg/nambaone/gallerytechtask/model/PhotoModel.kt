package kg.nambaone.gallerytechtask.model

import com.google.gson.annotations.SerializedName

data class PhotoModel(
    @SerializedName("photographer") val name: String?,
    @SerializedName("src") val photoUrl: SourcePhoto?,
    @SerializedName("alt") val photoDescription: String?
) {
    data class SourcePhoto (@SerializedName("original") val originalSize: String?)
}