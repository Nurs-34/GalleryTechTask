package kg.nambaone.gallerytechtask.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kg.nambaone.gallerytechtask.R

class PhotoListFragment : Fragment() {

    companion object {
        fun newInstance() = PhotoListFragment()
    }

    private lateinit var viewModel: PhotoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}