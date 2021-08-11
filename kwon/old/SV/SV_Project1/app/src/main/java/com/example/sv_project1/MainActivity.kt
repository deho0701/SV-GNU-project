package com.example.sv_project1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFrag(R.id.home)

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemReselectedListener {

            val ft = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.home -> {
                    ft.replace(R.id.main_frame, HomeFragment())
                }
                R.id.history -> {
                    ft.replace(R.id.main_frame, HistoryFragment())
                }
                R.id.myPage -> {
                    ft.replace(R.id.main_frame, MypageFragment())
                }
            }
            ft.addToBackStack(null)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ft.commit()
            true

        }
    }

    private fun setFrag(fragID: Int) {

        val ft = supportFragmentManager.beginTransaction()

        when (fragID) {
            R.id.home -> {
                ft.replace(R.id.main_frame, HomeFragment())
            }
            R.id.history -> {
                ft.replace(R.id.main_frame, HistoryFragment())
            }
            R.id.myPage -> {
                ft.replace(R.id.main_frame, MypageFragment())
            }
        }
        ft.addToBackStack(null)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
        true
    }
}

