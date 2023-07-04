package kg.nambaone.gallerytechtask.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import kg.nambaone.gallerytechtask.R

class PhotoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PhotoDetailFragment()
    }

    private lateinit var viewModel: PhotoDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoDetailViewModel::class.java)

        val receivedString = arguments?.getString("photo url")
        val image = requireView().findViewById<ShapeableImageView>(R.id.image_view_photo)
        Log.e("AAAAAA", receivedString.toString())

        Glide.with(this).load(receivedString).into(image)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}