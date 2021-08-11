package com.example.sv_project1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sv_app.setting_page
import kotlinx.android.synthetic.main.fragment_mypage.*

class MypageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mypage,container,false)
        return view

        personal_information.setOnClickListener { // 버튼 클릭시 할 행동
            val nextIntent = Intent(activity, setting_page::class.java)
            startActivity(nextIntent)  // 화면 전환하기
        }
    }
}