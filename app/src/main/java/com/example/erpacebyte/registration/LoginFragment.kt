package com.example.erpacebyte.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.example.erpacebyte.R


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<Button>(R.id.loginBtn).setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_paymentScreenFragment)
        }

        return view
    }

}