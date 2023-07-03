package kg.nambaone.gallerytechtask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kg.nambaone.gallerytechtask.R
import kg.nambaone.gallerytechtask.adapters.PhotoAdapter
import kg.nambaone.gallerytechtask.model.PhotoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PhotoListFragment : Fragment() {

    companion object {
        fun newInstance() = PhotoListFragment()
    }

    private lateinit var viewModel: PhotoListViewModel
    private lateinit var photoList: List<PhotoModel>
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = GridLayoutManager(context, 2)

        recyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            delay(2000)
            adapter = PhotoAdapter(viewModel.photoList)
            recyclerView.adapter = adapter
            photoList = viewModel.photoList
            adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PhotoListViewModel::class.java]

    }

}