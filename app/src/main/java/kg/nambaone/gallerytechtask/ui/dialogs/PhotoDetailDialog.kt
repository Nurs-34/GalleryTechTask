package kg.nambaone.gallerytechtask.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import kg.nambaone.gallerytechtask.R
import kg.nambaone.gallerytechtask.databinding.DialogPhotoDetailBinding

class PhotoDetailDialog : DialogFragment() {

    private var _binding: DialogPhotoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogPhotoDetailBinding.inflate(LayoutInflater.from(requireContext()))

        val photoUrl = arguments?.getString(ARG_PHOTO_URL)
        Glide.with(this)
            .load(photoUrl)
            .optionalFitCenter()
            .placeholder(R.drawable.ic_image_placeholder_24)
            .error(R.drawable.ic_error_24)
            .into(binding.imageViewPhoto)

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PHOTO_URL = "photo_url"

        fun newInstance(photoUrl: String?): PhotoDetailDialog {
            val args = Bundle().apply {
                putString(ARG_PHOTO_URL, photoUrl)
            }
            val dialog = PhotoDetailDialog()
            dialog.arguments = args
            return dialog
        }
    }
}