package com.example.t2kao

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.t2kao.adapters.ProductsListAdapter
import com.example.t2kao.api.RetrofitInstance
import com.example.t2kao.databinding.FragmentProductsListBinding
import com.example.t2kao.models.Product
import kotlinx.coroutines.launch

class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    private var _binding: FragmentProductsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set spinner with categories
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.product_category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCategory.adapter = adapter
        }

        binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedType = parent.getItemAtPosition(position).toString()

                // call APIs to show items of that category
                lifecycleScope.launch {
                    val productsList = getProductsList(selectedType)

                    if (productsList.isEmpty()) {
                        Log.d("API", "Empty List Retrieved")
                        return@launch
                    }

                    // put data in listview
                    val adapter = ProductsListAdapter(requireContext(), productsList)
                    binding.lvProductsList.adapter = adapter
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.lvProductsList.setOnItemClickListener { adapterView, view, position, id ->
            val product = adapterView.getItemAtPosition(position) as Product
            val action = ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsFragment(product)
            findNavController().navigate(action)
        }
    }

    private suspend fun getProductsList(type: String): List<Product> {
        val apiService = RetrofitInstance.retrofitService

        val response = apiService.getProductsList(type)

        if (response.isSuccessful) {
            var data = response.body()

            if (data == null) {
                Log.d("API", "No data from API or some other error")
                return listOf()
            }

            Log.d("API", data.toString())
            return data.products
        }
        else {
            Log.d("API", "Failure in calling API")
            return listOf()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}