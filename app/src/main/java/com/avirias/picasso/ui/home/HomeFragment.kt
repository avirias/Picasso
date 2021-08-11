package com.avirias.picasso.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.avirias.picasso.databinding.FragmentHomeBinding
import com.avirias.picasso.domain.Resource.*
import com.avirias.picasso.domain.model.Photo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val photoAdapter by lazy { PhotoAdapter(this::onPhotoClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPhotos()

        viewModel.photos
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is Failure -> {
                    }
                    is Loading -> {

                    }
                    is Success -> photoAdapter.submitList(it.data)
                }
            }.catch {

            }.launchIn(lifecycleScope)


        binding.recyclerGrid.apply {
            adapter = photoAdapter
            layoutManager = GridLayoutManager(this@HomeFragment.context, 2)
        }
    }

    private fun onPhotoClick(photo: Photo) {
        val direction = HomeFragmentDirections.actionHomeFragmentToPhotoDetailFragment(photo)
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}