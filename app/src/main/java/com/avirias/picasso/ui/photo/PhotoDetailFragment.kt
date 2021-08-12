package com.avirias.picasso.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.avirias.picasso.databinding.FragmentPhotoDetailBinding
import com.avirias.picasso.util.extensions.hideLoader
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<PhotoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().hideLoader()
        Timber.d("received args is %s", args.photo)
        binding.item = args.photo
        val simpleDateFormat = SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault())
        val format = simpleDateFormat.format(args.photo.publishedAt)
        binding.time.text = format
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}