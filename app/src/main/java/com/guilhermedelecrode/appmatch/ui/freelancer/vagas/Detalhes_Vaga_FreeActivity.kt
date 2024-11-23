package com.guilhermedelecrode.appmatch.ui.freelancer.vagas

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.Feed_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico.Ordem_Servico_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.Perfil_FreeActivity

class Detalhes_Vaga_FreeActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_vaga_free)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        //Logica para chamar PopUp de oferta
        val btn_ofertas_free = findViewById<Button>(R.id.btn_oferta_free)

        btn_ofertas_free.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_oferta, null)

            val editTextPreco = dialogView.findViewById<EditText>(R.id.editTextPreco)
            val editTextPrazo = dialogView.findViewById<EditText>(R.id.editTextPrazo)

            AlertDialog.Builder(this)

                .setView(dialogView)
                .setPositiveButton("Enviar") { dialog, which ->
                    val preco = editTextPreco.text.toString()
                    val prazo = editTextPrazo.text.toString()
                    // Aqui você pode tratar as entradas do usuário
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_detalhes_vagas_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilFree -> {
                Intent(this, Perfil_FreeActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.ordemServicoFree -> {
                Intent(this, Ordem_Servico_FreeActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.paginaInicial -> {
                Intent(this, Feed_FreeActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.sairFree -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)

    }
}