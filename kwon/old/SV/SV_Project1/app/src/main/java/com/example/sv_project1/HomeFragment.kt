package com.example.sv_project1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cafe_button.setOnClickListener {
            val intent = Intent(activity, CafeListActivity::class.java)
            startActivity(intent)
        }

        restaurant_button.setOnClickListener {
            val intent = Intent(activity, RestaurantListActivity::class.java)
            startActivity(intent)
        }

    }
}