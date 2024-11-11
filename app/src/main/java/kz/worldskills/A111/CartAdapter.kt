package kz.worldskills.A111

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class CartItem(val image: Int, val name: String, val price: Double, var quantity: Int)

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onQuantityChanged: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemName: TextView = view.findViewById(R.id.itemName)
        val itemPrice: TextView = view.findViewById(R.id.itemPrice)
        val itemQuantity: TextView = view.findViewById(R.id.itemQuantity)
        val decreaseButton: Button = view.findViewById(R.id.decreaseQuantityButton)
        val increaseButton: Button = view.findViewById(R.id.increaseQuantityButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.itemImage.setImageResource(cartItem.image)
        holder.itemName.text = cartItem.name
        holder.itemPrice.text = "${cartItem.price} ₸"
        holder.itemQuantity.text = cartItem.quantity.toString()

        // Увеличить количество
        holder.increaseButton.setOnClickListener {
            cartItem.quantity++
            notifyItemChanged(position)
            onQuantityChanged(cartItem)
        }

        // Уменьшить количество
        holder.decreaseButton.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                notifyItemChanged(position)
                onQuantityChanged(cartItem)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
