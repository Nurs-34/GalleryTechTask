package kg.nambaone.gallerytechtask.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kg.nambaone.gallerytechtask.R

class PhotoDetailDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_photo_detail, null)

        // Настройте элементы интерфейса, например:
        val imageViewPhoto = view.findViewById<ImageView>(R.id.image_view_photo)
        val photoUrl = arguments?.getString("photo url")
        Glide.with(this).load(photoUrl).into(imageViewPhoto)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)


        return builder.create()
    }
}