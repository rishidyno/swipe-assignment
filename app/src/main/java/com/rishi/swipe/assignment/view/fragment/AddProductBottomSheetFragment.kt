package com.rishi.swipe.assignment.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rishi.swipe.assignment.databinding.FragmentAddProductBottomSheetBinding
import com.rishi.swipe.assignment.model.entity.ProductDTOItem
import com.rishi.swipe.assignment.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProductBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var fragmentAddProductBottomSheetBinding: FragmentAddProductBottomSheetBinding

    private lateinit var appViewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentAddProductBottomSheetBinding =
            FragmentAddProductBottomSheetBinding.inflate(inflater, container, false)
        appViewModel = ViewModelProvider(this)[AppViewModel::class.java]

        return fragmentAddProductBottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentAddProductBottomSheetBinding.btnAddProduct.setOnClickListener {
            addAProduct()
        }
        appViewModel.newProductAddedSuccess.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                fragmentAddProductBottomSheetBinding.progressBarAddProduct.visibility =
                    View.VISIBLE
                fragmentAddProductBottomSheetBinding.btnAddProduct.visibility = View.GONE
            } else {
                fragmentAddProductBottomSheetBinding.progressBarAddProduct.visibility = View.GONE
                fragmentAddProductBottomSheetBinding.btnAddProduct.visibility = View.VISIBLE
            }
        }

    }

    private fun addAProduct() {
        var name = fragmentAddProductBottomSheetBinding.editTextProductName.text.toString()
        var type = fragmentAddProductBottomSheetBinding.editTextProductType.text.toString()
        var price = fragmentAddProductBottomSheetBinding.editTextProductPrice.text.toString().toDouble()
        var tax = fragmentAddProductBottomSheetBinding.editTextProductTax.text.toString().toDouble()
        var image = fragmentAddProductBottomSheetBinding.editTextImageURL.text.toString()

//        var name="name"
//        var type="code"
//        var price=99999999999999999.9
//        var tax=99999999.9
//        var imageUrl="https://linkedin.com/in/yash10019coder"


        if (validateFields(name, type, price, tax, image)) {

            val newProduct =
                ProductDTOItem(
                    productName = name,
                    price = price,
                    productType = type,
                    tax = tax,
                    image = image
                )


            CoroutineScope(Dispatchers.Main).launch {
                appViewModel.postProduct(newProduct)
            }
            dismiss()
        }

    }

    private fun validateFields(
        name: String,
        type: String,
        price: Double,
        tax: Double,
        image: String,
    ): Boolean {
        return when {
            name.isBlank() -> {
                showError(
                    fragmentAddProductBottomSheetBinding.editTextProductName,
                    "Product name is required"
                )
                false
            }

            type.isBlank() -> {
                showError(
                    fragmentAddProductBottomSheetBinding.editTextProductType,
                    "Product type is required"
                )
                false
            }

            price.toString().isBlank() -> {
                showError(
                    fragmentAddProductBottomSheetBinding.editTextProductPrice,
                    "Product price is required"
                )
                false
            }

            tax.toString().isBlank() -> {
                showError(
                    fragmentAddProductBottomSheetBinding.editTextProductTax,
                    "Product tax is required"
                )
                false
            }
            image.isBlank() -> {
                showError(
                    fragmentAddProductBottomSheetBinding.editTextProductTax,
                    "Product tax is required"
                )
                false
            }
            // Add additional validation rules as needed
            else -> true
        }
    }

    private fun showError(editText: View, errorMessage: String) {
        if (editText is com.google.android.material.textfield.TextInputEditText) {
            editText.error = errorMessage
            editText.requestFocus()
        }
    }
}
