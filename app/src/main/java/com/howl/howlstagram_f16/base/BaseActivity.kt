package com.howl.howlstagram_f16.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.howl.howlstagram_f16.R
import kotlinx.android.synthetic.main.menu.*

open class BaseActivity : AppCompatActivity() {

    private var backKeyPressedTime: Long = 0
    lateinit var pref: SharedPreferences

    lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        pref = getSharedPreferences("ref", Context.MODE_PRIVATE)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }




        // 검색 화면
//        search.setOnClickListener {
//            val i = Intent(applicationContext, ::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//            search.setBackgroundColor(Color.GRAY)
//
//        }


        // 평가 화면
//        star.setOnClickListener {
//            val i = Intent(applicationContext, EvaluationActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//            star.setBackgroundColor(Color.GRAY)
//
//        }

        // 소셜(sns) 화면
//        heart.setOnClickListener {
//            var detailViewFragment = DetailViewFragment()
//            supportFragmentManager.beginTransaction().replace(R.id.acti,detailViewFragment).commit()
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//            heart.setBackgroundColor(Color.GRAY)
//
//        }

        // 설정(myPage) 화면
//        person.setOnClickListener {
//            val i = Intent(applicationContext, SettingActivity::class.java)
//            startActivity(i)
//            overridePendingTransition(R.anim.fadein, R.anim.fadeout)
//            person.setBackgroundColor(Color.GRAY)
//
//        }


    }


