package com.example.projectfigma.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.projectfigma.Entites.Address
import com.example.projectfigma.R

class AddressAdapter(
    private var addresses: List<Address>,
    private val onAddressSelected: (Int) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.addressIcon)
        val radioButton: RadioButton = view.findViewById(R.id.addressRadio)
    }

    fun updateData(newAddresses: List<Address>) {
        addresses = newAddresses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_address, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address = addresses[position]

        // Set icon based on address name
        val iconRes = when {
            address.name.contains("home", ignoreCase = true) -> R.drawable.ic_home
            else -> R.drawable.ic_location
        }
        holder.icon.setImageResource(iconRes)

        holder.radioButton.text = "${address.name}\n${address.address}"
        holder.radioButton.isChecked = address.isDefault

        holder.radioButton.setOnClickListener {
            onAddressSelected(address.id ?: return@setOnClickListener)
        }
    }

    override fun getItemCount() = addresses.size
}