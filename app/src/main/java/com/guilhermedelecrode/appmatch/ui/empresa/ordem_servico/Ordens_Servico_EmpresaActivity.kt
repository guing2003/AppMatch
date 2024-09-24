package com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.empresa.feed.Feed_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.Perfil_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.Vagas_Cadastradas_EmpresaActivity

class Ordens_Servico_EmpresaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ordens_servico_empresa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onResume()

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
        menuInflater.inflate(R.menu.menu_item_ordem_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilEmpresa -> {
                Intent(this, Perfil_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.paginaInicial -> {
                Intent(this, Feed_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.novoServico -> {
                Intent(this, Feed_EmpresaActivity::class.java).apply { //Criar esta tela
                    startActivity(this)
                }
            }
            R.id.sair -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onResume() {
        super.onResume()
        Log.d("MyActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}