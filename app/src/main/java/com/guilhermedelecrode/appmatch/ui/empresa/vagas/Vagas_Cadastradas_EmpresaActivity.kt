package com.guilhermedelecrode.appmatch.ui.empresa.vagas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R

class Vagas_Cadastradas_EmpresaActivity : AppCompatActivity() {

    lateinit var rvVagas_Cadastradas : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vagas_cadastradas_empresa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        rvVagas_Cadastradas = findViewById(R.id.rvVagas_Cadastradas)


        // Habilitar suporte a ActionBar personalizada
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Definir o layout customizado na ActionBar
        val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
        supportActionBar?.customView = actionBarLayout
        supportActionBar?.setDisplayShowCustomEnabled(true)

        // Encontrar o botão no layout customizado da ActionBar
        val backButton = actionBarLayout.findViewById<Button>(R.id.action_bar_button)

        // Definir ação para o botão Voltar
        backButton.setOnClickListener {
            // Voltar à tela anterior ou realizar alguma ação
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.perfilEmpresa ->{
                Intent(this, Vagas_Cadastradas_EmpresaActivity::class.java).apply{
                    startActivity(this)
                }
            }
            R.id.logout -> {
                Intent(this, Vagas_Cadastradas_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}