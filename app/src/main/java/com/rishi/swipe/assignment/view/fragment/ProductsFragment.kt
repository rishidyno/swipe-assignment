package com.rishi.swipe.assignment.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rishi.swipe.assignment.databinding.FragmentProductsBinding
import com.rishi.swipe.assignment.util.ConnectivityObserver
import com.rishi.swipe.assignment.util.NetworkConnectivityObserver
import com.rishi.swipe.assignment.view.adapters.ProductAdapter
import com.rishi.swipe.assignment.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var fragmentProductsBinding: FragmentProductsBinding

    private lateinit var appViewModel: AppViewModel

    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProductsBinding = FragmentProductsBinding.inflate(inflater, container, false)
        appViewModel = ViewModelProvider(this)[AppViewModel::class.java]

        return fragmentProductsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentProductsBinding.rvProductLis.layoutManager = LinearLayoutManager(requireActivity())

        val productAdapter = ProductAdapter(this@ProductsFragment)

        fragmentProductsBinding.rvProductLis.adapter = productAdapter


        appViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                fragmentProductsBinding.productBar.visibility =
                    View.VISIBLE
            } else {
                fragmentProductsBinding.productBar.visibility = View.GONE
            }
        }

        appViewModel.product.observe(viewLifecycleOwner) {
            productAdapter.products = it
            productAdapter.notifyDataSetChanged()
        }

        getAllProducts()

        fragmentProductsBinding.fabAddProduct.setOnClickListener {
            val bottomSheet = AddProductBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

        connectivityObserver = NetworkConnectivityObserver(requireContext())
//        connectivityObserver.observe().run {
//            this.onEach {
//                Log.i("tag", it.toString())
//                println(it)
//                requireActivity().runOnUiThread {
//                    fragmentProductsBinding.noInternetText.text = "hello"
//                }
//            }
//        }
        connectivityObserver.observe().onEach {
            Log.i("tag", it.toString())
//            println(it)
            requireActivity().runOnUiThread {
                val networkStatus = "Network Status:  $it"
                fragmentProductsBinding.noInternetText.text = networkStatus
            }
        }.launchIn(lifecycleScope)
    }

    private fun getAllProducts() {
        CoroutineScope(Dispatchers.Main).launch {
            appViewModel.getAllProducts()
        }
    }
}
