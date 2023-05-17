package com.example.t2kao

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.t2kao.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        Glide.with(requireContext())
            .load(product.thumbnail)
            .into(binding.ivProductImage)

        binding.tvProductName.text = product.title
        binding.tvProductDesc.text = product.description
        binding.tvProductPrice.text = product.price.toString()
        binding.tvProductBrand.text = product.brand
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}