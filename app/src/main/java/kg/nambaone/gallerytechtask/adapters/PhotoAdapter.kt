package kg.nambaone.gallerytechtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kg.nambaone.gallerytechtask.databinding.PhotoItemBinding
import kg.nambaone.gallerytechtask.model.PhotoModel

class PhotoAdapter(
    private val values: List<PhotoModel>
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val photographName: TextView = binding.textViewName
        private val photoDescription: TextView = binding.textViewDescription
        private val photo: ShapeableImageView = binding.shapeImageView

        fun bind(item: PhotoModel) {
            photographName.text = item.name
            if (!item.photoDescription.equals(""))
                photoDescription.text = item.photoDescription
            else
                photoDescription.text = "No description"



            Glide.with(itemView).load(item.photoUrl?.originalSize).into(photo)
        }
    }

}