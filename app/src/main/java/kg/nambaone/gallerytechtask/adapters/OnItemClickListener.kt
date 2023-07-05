package kg.nambaone.gallerytechtask.adapters

import kg.nambaone.gallerytechtask.model.PhotoModel

interface OnItemClickListener {
    fun onItemClick(photoModel: PhotoModel)
}