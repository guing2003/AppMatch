package com.guilhermedelecrode.appmatch.ui.cadastro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.guilhermedelecrode.appmatch.MainActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.common.AbstractActivity

class SplashScreenActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        configActionBarLogin()

        val logoImage = findViewById<ImageView>(R.id.logo_image_splash_screen_activity)
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_screen_anim)
        logoImage.startAnimation(animation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
