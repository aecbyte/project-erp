package com.example.erpacebyte

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

class PaymentScreenFragment : Fragment() {
    private var isUPILayoutExpanded = false
    private var isAddUPICardExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_payment_screen, container, false)

        val mainUPICard = view.findViewById<CardView>(R.id.upiCard)
        val expandableUPILayout = view.findViewById<LinearLayout>(R.id.upiPaymentLinearLayout)
        val mainAddUPICard = view.findViewById<CardView>(R.id.addUPICard)
        val expandedAddUPILayout = view.findViewById<CardView>(R.id.addUPICardExpanded)
        mainUPICard.setOnClickListener {
            if(isUPILayoutExpanded){
                collapseView(expandableUPILayout)
            }
            else{
                expandView(expandableUPILayout)
            }
            isUPILayoutExpanded = !isUPILayoutExpanded
        }
        mainAddUPICard.setOnClickListener {
            if (isAddUPICardExpanded){
                collapseView(expandedAddUPILayout)
            }
            else{
                expandView(expandedAddUPILayout)
            }
            isAddUPICardExpanded = !isAddUPICardExpanded
        }


        return view
    }

    private fun expandView(view: View) {
        view.visibility = View.VISIBLE
        val animator = ObjectAnimator.ofFloat(view, "translationY", -100f, 0f)
        animator.duration = 200
        animator.start()
    }

    private fun collapseView(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -100f)
        animator.duration = 200
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.visibility = View.GONE
            }
        })
        animator.start()
    }

}