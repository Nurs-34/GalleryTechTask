package kg.nambaone.gallerytechtask.ui

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import kg.nambaone.gallerytechtask.MainActivity
import kg.nambaone.gallerytechtask.R
import kg.nambaone.gallerytechtask.adapters.OnItemClickListener
import kg.nambaone.gallerytechtask.adapters.PhotoAdapter
import kg.nambaone.gallerytechtask.model.PhotoModel
import kg.nambaone.gallerytechtask.ui.dialogs.PhotoDetailDialog
import kg.nambaone.gallerytechtask.utils.Constants.Companion.page
import kg.nambaone.gallerytechtask.utils.Constants.Companion.perPage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random

class PhotoListFragment : Fragment() {

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
        viewModel = ViewModelProvider(this)[PhotoListViewModel::class.java]
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = GridLayoutManager(context, 2)
        val lottieAnimation = view.findViewById<LottieAnimationView>(R.id.animation_view)

        val noDescStringRes = requireContext().getString(R.string.no_description)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)

        recyclerView.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.myLoadingStateFlow.collect { value ->
                when (value) {
                    true -> {
                        delay(1000) //to upload photos
                        lottieAnimation.visibility = View.GONE
                        adapter = PhotoAdapter(viewModel.photoList, noDescStringRes) {
                            val bundle = Bundle()
                            bundle.putString("photo url", it.photoUrl?.originalSize)
                            val dialog = PhotoDetailDialog()
                            dialog.arguments = bundle
                            dialog.show(
                                requireActivity().supportFragmentManager,
                                "Photo detail dialog"
                            )
                        }
                        recyclerView.adapter = adapter
                        photoList = viewModel.photoList
                        adapter.notifyDataSetChanged()
                        recyclerView.visibility = View.VISIBLE
                    }

                    false -> {
                        lottieAnimation.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.photoListActionFlow.onEach { action ->
            when(action){
                is PhotoListViewModel.PhotoListAction.ShowInternetErrorToast ->  Toast.makeText(
                    requireContext(), getString(R.string.please_check_your_internet_connection), Toast.LENGTH_LONG
                ).show()
            }
        }.launchIn(lifecycleScope)

        swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                var randomPage = Random.nextInt(1, 11)
                viewModel.loadPhotoList(randomPage, perPage)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}