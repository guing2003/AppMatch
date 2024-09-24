package com.guilhermedelecrode.appmatch.ui.freelancer.perfil

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
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.Vagas_Cadastradas_EmpresaActivity

class Edit_Perfil_FreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_perfil_free)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_cancelar_edit_free = findViewById<Button>(R.id.btn_cancelar_edit_free)
        val btn_salvar_edit_free = findViewById<Button>(R.id.btn_salvar_edit_free)
        btn_salvar_edit_free.setOnClickListener{
            val intent = Intent(this, Perfil_FreeActivity::class.java)
            startActivity(intent)
        }
        btn_cancelar_edit_free.setOnClickListener{
            val intent = Intent(this, Perfil_FreeActivity::class.java)
            startActivity(intent)
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
        menuInflater.inflate(R.menu.menu_item_edit_perfil_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfil -> {
                Intent(this, Vagas_Cadastradas_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.newService -> {
                Intent(this, Vagas_Cadastradas_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onResume() {
        super.onResume()
        Log.d("MyActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}