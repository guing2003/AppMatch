package com.guilhermedelecrode.appmatch.ui.cadastro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.MainActivity
import com.guilhermedelecrode.appmatch.R

class SplashScreenActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Configurar a ActionBar geral
        configActionBarLogin()


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        },3000)
    }
}