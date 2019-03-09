package com.nil_projects.locationsilencer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread{
            Thread.sleep(11000)
            var intent = Intent(this,MainActivity :: class.java)
            startActivity(intent)
        }.start()
    }
}
