package com.guilhermedelecrode.appmatch

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

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
        // Configurações da barra de navegação e status para telas de login
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)
        window.statusBarColor = Color.parseColor("#00537D")
        supportActionBar?.hide()
    }

    fun configActionBarGeral() {
        // Configurações da barra de navegação e status para telas gerais
        window.statusBarColor = Color.parseColor("#00537D")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        // Configuração da ActionBar personalizada
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayShowTitleEnabled(false)

            val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
            customView = actionBarLayout
            setDisplayShowCustomEnabled(true)

            // Configurar botão de voltar
            val backButton = actionBarLayout.findViewById<Button>(R.id.action_bar_button)
            backButton.setOnClickListener { onBackPressed() }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("AbstractActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}
