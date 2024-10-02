package com.guilhermedelecrode.appmatch.ui.freelancer.feed

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
import com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico.Ordem_Servico_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.Perfil_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.vagas.Detalhes_Vaga_FreeActivity

class Feed_FreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feed_free)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btn_detalhes_free = findViewById<Button>(R.id.btn_detalhes_free)

        btn_detalhes_free.setOnClickListener {
            val intent = Intent(this, Detalhes_Vaga_FreeActivity::class.java)
            startActivity(intent)
        }
        onResume()

        window.statusBarColor = Color.parseColor("#00537D")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

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
        menuInflater.inflate(R.menu.menu_item_feed_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilFree -> {
                Intent(this, Perfil_FreeActivity::class.java).apply {
                    startActivity(this)
                }
                return true
            }
            R.id.ordemServicoFree ->{
                Intent(this, Ordem_Servico_FreeActivity::class.java).apply {
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
