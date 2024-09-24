package com.guilhermedelecrode.appmatch.ui.empresa.feed

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
import com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico.Ordens_Servico_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.Perfil_EmpresaActivity

class Feed_EmpresaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feed_empresa)
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
        menuInflater.inflate(R.menu.menu_item_feed_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilEmpresa -> {
                // Criando a Intent
                val feed = Intent(this, Perfil_EmpresaActivity::class.java)
                startActivity(feed) // Iniciando a atividade
                true // Indica que o evento foi tratado
            }

            R.id.ordemServicoEmpresa -> {
                // Criando a Intent para
                val ordemServico = Intent(this, Ordens_Servico_EmpresaActivity::class.java)
                startActivity(ordemServico) // Iniciando a atividade
                true // Indica que o evento foi tratado
            }
            R.id.sair -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}
