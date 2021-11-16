package com.example.sv_project1.fargment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sv_app.SettingPageActivity
import com.example.sv_project1.R
import kotlinx.android.synthetic.main.fragment_mypage.*

class MypageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_mypage,container,false)
        return view
    }
}