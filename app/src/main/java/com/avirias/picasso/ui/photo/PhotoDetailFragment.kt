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
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri

import androidx.core.content.FileProvider
import com.avirias.picasso.BuildConfig
import java.io.File
import java.io.FileOutputStream


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
        setOnShareListener()
    }

    private fun setOnShareListener() {
        binding.shareButton.setOnClickListener {
            try {
                val uri = if (args.photo.picture.startsWith("content:")) {
                    Uri.parse(args.photo.picture)
                } else {
                    val bitmap = ((binding.flicker.drawable) as BitmapDrawable).bitmap
                    val file =
                        File(requireContext().filesDir, "${args.photo.title}_${Date().time}.png")
                    val fileOutputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.close()
                    FileProvider.getUriForFile(
                        requireContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        file
                    )
                }

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/*"
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    setDataAndType(uri, requireActivity().contentResolver.getType(uri))
                    putExtra(Intent.EXTRA_STREAM, uri)
                }
                startActivity(Intent.createChooser(intent, "Share with..."))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}