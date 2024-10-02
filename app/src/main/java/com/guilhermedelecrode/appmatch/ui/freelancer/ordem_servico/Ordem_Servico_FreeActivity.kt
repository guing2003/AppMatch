package com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.Feed_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.Perfil_FreeActivity
import kotlin.math.log

class Ordem_Servico_FreeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ordem_servico_free)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onResume()

        window.statusBarColor = Color.parseColor("#00537D")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        // Encontre a ImageView no layout
        val btn_editar_ordem_servico_free = findViewById<Button>(R.id.btn_editar_ordem_servico_free)

        btn_editar_ordem_servico_free.setOnClickListener {
            // Inflate o layout do dialog
            val dialogView = layoutInflater.inflate(R.layout.dialog_status_ordem_servico, null)

            // Localize o Spinner no layout do dialog
            val spinner = dialogView.findViewById<Spinner>(R.id.spinnerStatus)

            // Crie um ArrayAdapter para o Spinner com as opções de status
            val options = arrayOf("Em andamento", "Concluído", "Cancelada")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Crie o AlertDialog
            AlertDialog.Builder(this)
                .setTitle("Status: ordem de serviço")
                .setView(dialogView)
                .setPositiveButton("Salvar") { dialog, which ->
                    val selectedStatus = spinner.selectedItem.toString()
                    // Aqui você pode tratar a opção selecionada
                    Toast.makeText(this, "Status selecionado: $selectedStatus", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
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
        menuInflater.inflate(R.menu.menu_item_ordem_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilFree -> {
                Intent(this, Perfil_FreeActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.paginaInicial -> {
                Intent(this, Feed_FreeActivity::class.java).apply {
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
