package com.example.sv_project1

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.sv_project1.navigation.HistoryFragment
import com.example.sv_project1.navigation.HomeFragment
import com.example.sv_project1.navigation.MypageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val historyFragment by lazy { HistoryFragment() }
    private val mypageFragment by lazy { MypageFragment() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNaviBar()
    }

    private fun initNaviBar() {
        bottomNavigationView.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home ->{
                        supportFragmentManager.beginTransaction().
                        replace(R.id.main_frame, homeFragment).commit()
                    }
                    R.id.history -> {
                        supportFragmentManager.beginTransaction().
                        replace(R.id.main_frame, historyFragment).commit()
                    }
                    R.id.myPage -> {
                        supportFragmentManager.beginTransaction().
                        replace(R.id.main_frame, mypageFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.home
        }
    }

}

