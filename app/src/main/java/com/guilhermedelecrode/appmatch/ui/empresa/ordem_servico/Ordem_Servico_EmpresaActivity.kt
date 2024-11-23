package com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
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
import com.guilhermedelecrode.appmatch.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.empresa.feed.Feed_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.Perfil_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.Cadastrar_VagaActivity

class Ordem_Servico_EmpresaActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordens_servico_empresa)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        // Encontre a ImageView no layout
        val img_editar_ordem_servico = findViewById<ImageView>(R.id.img_editar_ordem_servico)
        val img_avaliar = findViewById<ImageView>(R.id.img_avaliar)

        img_avaliar.setOnClickListener{
            val dialogAvaliarView = layoutInflater.inflate(R.layout.dialog_avaliar, null)

            // Crie o AlertDialog
            AlertDialog.Builder(this)
                .setTitle("Avaliação")
                .setView(dialogAvaliarView)
                .setPositiveButton("Salvar") { dialog, which ->

                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }

        img_editar_ordem_servico.setOnClickListener {
            // Inflate o layout do dialog
            val dialogEditOrdemServicoView = layoutInflater.inflate(R.layout.dialog_status_ordem_servico, null)

            // Localize o Spinner no layout do dialog
            val spinner = dialogEditOrdemServicoView.findViewById<Spinner>(R.id.spinnerStatus)

            // Crie um ArrayAdapter para o Spinner com as opções de status
            val options = arrayOf("Em andamento", "Concluído", "Cancelada")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Crie o AlertDialog
            AlertDialog.Builder(this)
                .setTitle("Status: ordem de serviço")
                .setView(dialogEditOrdemServicoView)
                .setPositiveButton("Salvar") { dialog, which ->
                    val selectedStatus = spinner.selectedItem.toString()
                    // Aqui você pode tratar a opção selecionada
                    Toast.makeText(this, "Status selecionado: $selectedStatus", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
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
                    finish()
                }
            }
            R.id.paginaInicial -> {
                Intent(this, Feed_EmpresaActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            R.id.novoServico -> {
                Intent(this, Cadastrar_VagaActivity::class.java).apply { //Criar esta tela
                    startActivity(this)
                    finish()
                }
            }
            R.id.sair -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)

    }
}