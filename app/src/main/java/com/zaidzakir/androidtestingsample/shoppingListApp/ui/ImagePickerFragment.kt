package com.zaidzakir.androidtestingsample.shoppingListApp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zaidzakir.androidtestingsample.R
import com.zaidzakir.androidtestingsample.shoppingListApp.adapters.ImageAdapter
import com.zaidzakir.androidtestingsample.shoppingListApp.utils.Constants.GRID_SPAN_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject

/**
 *Created by Zaid Zakir
 */
@AndroidEntryPoint
class ImagePickerFragment @Inject constructor(
    val imageAdapter : ImageAdapter
):Fragment(R.layout.fragment_image_pick) {
    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            shoppingViewModel.setImageUrl(it)
        }
    }

    private fun setupRecyclerView() {
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }
}