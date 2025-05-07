package com.example.projectfigma.Fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.projectfigma.R

class HeaderButtonsFragment : Fragment(R.layout.fragment_header_buttons) {

    interface Listener {
        fun onCartClicked()
        fun onProfileClicked()
        fun onNotificationsClicked()
    }

    private var listener: Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.cart_button)
            .setOnClickListener { listener?.onCartClicked() }

        view.findViewById<ImageView>(R.id.profile_button)
            .setOnClickListener { listener?.onProfileClicked() }

        view.findViewById<ImageView>(R.id.notification_button)
            .setOnClickListener { listener?.onNotificationsClicked() }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
