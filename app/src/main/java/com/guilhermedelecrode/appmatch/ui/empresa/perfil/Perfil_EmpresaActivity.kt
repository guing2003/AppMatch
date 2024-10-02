package com.guilhermedelecrode.appmatch.ui.empresa.perfil

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.empresa.feed.Feed_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico.Ordem_Servico_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.Cadastrar_VagaActivity

class Perfil_EmpresaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil_empresa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onResume()

        window.statusBarColor = Color.parseColor("#00537D")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        val btn_edit_perfil_empresa = findViewById<Button>(R.id.btn_edit_perfil_empresa)
        btn_edit_perfil_empresa.setOnClickListener{
            val intent = Intent(this, Edit_Perfil_EmpresaActivity::class.java)
            startActivity(intent)
        }

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
        menuInflater.inflate(R.menu.menu_item_perfil_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilPaginaInicial -> {
                Intent(this, Feed_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.perfilNovoServico -> {
                Intent(this, Cadastrar_VagaActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.perfilOrdemServicoEmpresa -> {
                Intent(this, Ordem_Servico_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.perfilSair -> {
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