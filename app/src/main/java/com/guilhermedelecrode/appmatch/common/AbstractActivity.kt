package com.guilhermedelecrode.appmatch.common

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.guilhermedelecrode.appmatch.R

abstract class AbstractActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                Log.d("AbstractActivity", "Classe base inicializada: ${this::class.java.simpleName}")
    }

    fun configActionBarCadastro(){
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#00537D")
    }

    fun configActionBarLogin() {
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)
        window.statusBarColor = Color.parseColor("#00537D")
        supportActionBar?.hide()
    }

    fun configActionBarGeral() {
        window.statusBarColor = Color.parseColor("#00537D")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayShowTitleEnabled(false)

            val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
            customView = actionBarLayout
            setDisplayShowCustomEnabled(true)

            val backButton = actionBarLayout.findViewById<Button>(R.id.action_bar_button)
            backButton.setOnClickListener { onBackPressed() }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("AbstractActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}
