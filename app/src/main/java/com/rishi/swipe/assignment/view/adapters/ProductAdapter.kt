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


/**
 * Adapter for displaying a list of products in a RecyclerView.
 *
 * @param fragment The fragment associated with the adapter.
 * @see RecyclerView.Adapter
 */
class ProductAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    // List of products to be displayed in the RecyclerView
    var products: MutableList<ProductDTOItem> = mutableListOf()

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemProductLayoutBinding: ItemProductLayoutBinding =
            ItemProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemProductLayoutBinding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        Log.i("tag", "setting item number" + position)

        // Set the product information to the ViewHolder's views
        holder.name.text = product.productName
        holder.price.text = product.price.toString()
        holder.type.text = product.productType
        holder.tax.text = product.tax.toString()

        // Load the product image using Glide
        Glide.with(fragment)
            .load(product.image)
            .centerInside()
            .error(R.drawable.no_image_found)
            .into(holder.image)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set held by the adapter.
     */
    override fun getItemCount(): Int {
        return products.size
    }

    /**
     * ViewHolder for holding the views associated with a product item.
     *
     * @param itemView Binding class generated by view binding for the product item layout.
     * @see RecyclerView.ViewHolder
     */
    class ViewHolder(itemView: ItemProductLayoutBinding) : RecyclerView.ViewHolder(itemView.root) {
        val image = itemView.productImage
        val price = itemView.productPrice
        val name = itemView.productName
        val type = itemView.productType
        val tax = itemView.productTax
    }
}
