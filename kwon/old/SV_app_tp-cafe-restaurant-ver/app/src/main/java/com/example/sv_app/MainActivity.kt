package com.example.sv_app



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent

import android.widget.ImageButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnMove = findViewById<ImageButton>(R.id.personal_information);
        val nextIntent = Intent(this,setting_page::class.java)
        btnMove.setOnClickListener { // 버튼 클릭시 할 행동
            startActivity(nextIntent)  // 화면 전환하기
        }


    }
}