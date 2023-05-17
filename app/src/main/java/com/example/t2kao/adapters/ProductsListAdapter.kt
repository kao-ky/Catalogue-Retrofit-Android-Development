package com.example.t2kao.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.t2kao.databinding.ListItemProductBinding
import com.example.t2kao.models.Product

class ProductsListAdapter(context: Context, itineraryList: List<Product>)
    : ArrayAdapter<Product>(context, 0, itineraryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ListItemProductBinding

        if (convertView != null) {
            binding = ListItemProductBinding.bind(convertView)
        }
        else {
            binding = ListItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        }

        val itinerary = getItem(position)

        itinerary?.let {
            Glide.with(context)
                .load(it.thumbnail)
                .into(binding.ivProductImage)

            binding.tvProductName.text = it.title
            binding.tvProductRating.text = "Rating: ${it.rating}"
        }

        return binding.root
    }
}