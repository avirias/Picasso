package com.avirias.picasso.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.TakePicture
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.avirias.picasso.BuildConfig
import com.avirias.picasso.common.showErrorDialog
import com.avirias.picasso.databinding.FragmentHomeBinding
import com.avirias.picasso.domain.Resource.*
import com.avirias.picasso.domain.model.Photo
import com.avirias.picasso.util.extensions.hideLoader
import com.avirias.picasso.util.extensions.showLoader
import com.avirias.picasso.util.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.io.File
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val photoAdapter by lazy { PhotoAdapter(this::onPhotoClick) }
    private var currentImageUri: String? = null

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

//        viewModel.getPhotos()

        viewModel.photos
            .flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is Failure -> {
                        requireActivity().hideLoader()
                        Timber.e(it.throwable)
                        showErrorDialog()
                    }
                    is Loading -> requireActivity().showLoader()
                    is Success -> {
                        Timber.d("data received")
                        requireActivity().hideLoader()
                        photoAdapter.submitList(it.data + viewModel.capturedImages)
                    }
                }
            }.catch { e ->
                Timber.e(e)
                showErrorDialog()
            }.launchIn(lifecycleScope)


        binding.recyclerGrid.apply {
            adapter = photoAdapter
            layoutManager = GridLayoutManager(this@HomeFragment.context, 2)
        }

        binding.fab.setOnClickListener {
            val camera = Manifest.permission.CAMERA
            if (shouldShowRequestPermissionRationale(camera))
                showErrorDialog("Please enable camera permission in settings") {
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = uri
                    }
                    startActivity(intent)
                }
            else cameraPermission.launch(camera)
        }
    }

    private fun onPhotoClick(photo: Photo) {
        val direction = HomeFragmentDirections.actionHomeFragmentToPhotoDetailFragment(photo)
        findNavController().navigate(direction)
    }

    private val cameraPermission = registerForActivityResult(RequestPermission()) {
        if (it) {
            val file = File(requireContext().filesDir, "image_${Date().time}.jpg")
            val uriForFile = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".provider",
                file
            ).apply {
                currentImageUri = this.toString()
            }
            takePicture.launch(uriForFile)
        } else showErrorDialog("Camera permission required to capture an image")
    }

    private val takePicture = registerForActivityResult(TakePicture()) {
        if (it) {
            currentImageUri?.let { uri ->
                val date = Date()
                viewModel.capturedImages.add(
                    Photo(
                        id = UUID.randomUUID().toString(),
                        title = "From Camera",
                        publishedAt = date,
                        comment = "Captured from camera at ${date.hours}:${date.minutes} \r\n",
                        picture = uri
                    )
                )
            }
        } else toast("Error while saving image")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}