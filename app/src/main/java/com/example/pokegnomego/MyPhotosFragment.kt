package com.example.pokegnomego

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokegnomego.databinding.FragmentMyPhotosBinding
import java.io.File

class MyPhotosFragment : Fragment() {

    private var _binding: FragmentMyPhotosBinding? = null
    private val binding get() = _binding!!
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var photoList: List<String>
    private val REQUEST_READ_STORAGE_PERMISSION = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPhotosBinding.inflate(inflater, container, false)
        checkPermissions()
        return binding.root
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_STORAGE_PERMISSION)
        } else {
            setupRecyclerView()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_READ_STORAGE_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                setupRecyclerView()
            } else {
                // Permission denied
            }
        }
    }

    private fun setupRecyclerView() {
        val photosDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyAppImages")
        Log.d("setupRecyclerView", "Photos directory path: ${photosDir.absolutePath}")

        photoList = if (photosDir.exists()) {
            photosDir.listFiles()?.map { it.absolutePath } ?: emptyList()
        } else {
            emptyList()
        }

        Log.d("MyPhotosFragment", "Number of photos found: ${photoList.size}")

        photoAdapter = PhotoAdapter(photoList)
        binding.photoRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = photoAdapter
        }
        Log.d("MyPhotosFragment", "RecyclerView setup complete")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
