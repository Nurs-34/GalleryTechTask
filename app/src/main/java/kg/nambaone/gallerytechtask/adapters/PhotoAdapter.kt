package kg.nambaone.gallerytechtask.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kg.nambaone.gallerytechtask.R
import kg.nambaone.gallerytechtask.databinding.PhotoItemBinding
import kg.nambaone.gallerytechtask.model.PhotoModel

class PhotoAdapter(
    private val values: List<PhotoModel>,
    private val noDescriptionStringRes: String,
    private val onItemClickListener: (PhotoModel) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoAdapter.ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)

        holder.itemView.setOnClickListener { onItemClickListener(item) }
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
                photoDescription.text = noDescriptionStringRes

            Glide.with(itemView)
                .load(item.photoUrl?.mediumSize)
                .placeholder(R.drawable.ic_image_placeholder_24)
                .error(R.drawable.ic_error_24)
                .into(photo)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(){
        notifyDataSetChanged()
    }
}