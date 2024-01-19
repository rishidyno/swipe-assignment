package com.rishi.swipe.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rishi.swipe.assignment.model.database.DatabaseRepository
import com.rishi.swipe.assignment.model.entity.ProductDTOItem
import com.rishi.swipe.assignment.model.entity.ProductEntity
import com.rishi.swipe.assignment.model.mappers.toProductEntity
import com.rishi.swipe.assignment.model.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private var _product = MutableLiveData<MutableList<ProductDTOItem>>()
    val product: LiveData<MutableList<ProductDTOItem>> get() = _product

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _newProduct = MutableLiveData<ProductDTOItem>()
    val newProduct: LiveData<ProductDTOItem> get() = _newProduct


    private var _newProductAddedSuccess = MutableLiveData<Boolean>()
    val newProductAddedSuccess: LiveData<Boolean> get() = _newProductAddedSuccess

    suspend fun getAllProducts() {
        _isLoading.postValue(true)
        withContext(Dispatchers.IO) {
            val allProducts = networkRepository.getAllProducts()
            allProducts.getOrNull()?.let {
                _product.postValue(it)
                val productEntityList= it.map {
                    it.toProductEntity()
                }
                databaseRepository.cacheProductsToRoomDatabase(productEntityList)
            }

            _isLoading.postValue(false)
        }
    }


    suspend fun postProduct(productDTOItem: ProductDTOItem) {
        withContext(Dispatchers.IO) {
            _newProductAddedSuccess.postValue(true)
            val postResult = networkRepository.postProduct(productDTOItem)
            if (postResult.isSuccess) {
                postResult.getOrNull()?.let {
                    _newProduct.postValue(productDTOItem)
                    _newProductAddedSuccess.postValue(false)
                }
            }else{
                _newProductAddedSuccess.postValue(false)
            }
        }
    }

}