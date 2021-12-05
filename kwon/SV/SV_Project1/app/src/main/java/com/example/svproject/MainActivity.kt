package com.example.svproject


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.svproject.fargment.HistoryFragment
import com.example.svproject.fargment.HomeFragment
import com.example.svproject.fargment.MypageFragment
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val homeFragment by lazy { HomeFragment() }
    private val historyFragment by lazy { HistoryFragment() }
    private val mypageFragment by lazy { MypageFragment() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var id = intent.getStringExtra("id")

        initNaviBar(id)
    }

    private fun initNaviBar(id: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        bottomNavigationView.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home ->{
                        homeFragment.arguments = bundle
                        supportFragmentManager.beginTransaction().
                        replace(R.id.main_frame, homeFragment).commit()
                    }
                    R.id.history -> {
                        historyFragment.arguments = bundle
                        supportFragmentManager.beginTransaction().
                        replace(R.id.main_frame, historyFragment).commit()
                    }
                    R.id.myPage -> {
                        mypageFragment.arguments = bundle
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

