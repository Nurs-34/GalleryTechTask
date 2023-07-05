package kg.nambaone.gallerytechtask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kg.nambaone.gallerytechtask.R
import kg.nambaone.gallerytechtask.adapters.PhotoAdapter
import kg.nambaone.gallerytechtask.databinding.FragmentPhotoListBinding
import kg.nambaone.gallerytechtask.utils.Constants.Companion.perPage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.random.Random

class PhotoListFragment : Fragment() {

    private lateinit var binding: FragmentPhotoListBinding
    private lateinit var viewModel: PhotoListViewModel
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()

        lifecycleScope.launch {
            viewModel.myLoadingStateFlow.collect { value ->
                when (value) {
                    true -> showPhotoList()
                    false -> showLoadingAnimation()
                }
            }
        }

        viewModel.photoListActionFlow.onEach { action ->
            when (action) {
                is PhotoListViewModel.PhotoListAction.ShowInternetErrorToast -> Toast.makeText(
                    requireContext(),
                    getString(R.string.please_check_your_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
        }.launchIn(lifecycleScope)

        binding.swipeRefreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                val randomPage = Random.nextInt(1, 11)
                viewModel.loadPhotoList(randomPage, perPage)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        initViewModel()
        viewModel.onSaveInstanceState()
    }

    private fun initViewModel() {
        val viewModelFactory = SavedStateViewModelFactory(requireActivity().application, this)
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[PhotoListViewModel::class.java]
    }

    private fun initRecyclerView() {
        val currentOrientation = resources.configuration.orientation
        val spanCount = viewModel.calculateSpanCount(currentOrientation)
        val layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.recyclerView.layoutManager = layoutManager
    }

    private fun initAdapter() {
        adapter = PhotoAdapter(
            viewModel.photoListFlow.value,
            viewModel.getStringRes(requireContext())
        ) {
            viewModel.showPhotoDialog(requireActivity(), it)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun showPhotoList() {
        binding.animationView.visibility = View.GONE
        initAdapter()
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun showLoadingAnimation() {
        binding.animationView.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
}