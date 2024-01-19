package com.rishi.swipe.assignment.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rishi.swipe.assignment.R
import com.rishi.swipe.assignment.databinding.ItemProductLayoutBinding
import com.rishi.swipe.assignment.model.entity.ProductDTOItem
import com.rishi.swipe.assignment.model.entity.ProductEntity

class ProductAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var products: MutableList<ProductDTOItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemProductLayoutBinding: ItemProductLayoutBinding =
            ItemProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemProductLayoutBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        Log.i("tag", "setting item number" + position)

        holder.name.text = product.productName
        holder.price.text = product.price.toString()
        holder.type.text = product.price.toString()
        holder.tax.text = product.tax.toString()
        Glide.with(fragment)
            .load(product.image).centerInside()
            .error(R.drawable.no_image_found)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(itemView: ItemProductLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val image = itemView.productImage
        val price = itemView.productPrice
        val name = itemView.productName
        val type = itemView.productType
        val tax = itemView.productTax
    }
}