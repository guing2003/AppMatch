package com.guilhermedelecrode.appmatch.ui.freelancer.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.guilhermedelecrode.appmatch.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.adapter.empresa.Feed_EmpresaAdapter
import com.guilhermedelecrode.appmatch.adapter.freelancer.Feed_FreelancerAdapter
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.empresa.feed.Feed_EmpresaActivity.Companion.VAGA
import com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico.Ordem_Servico_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.Perfil_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.vagas.Detalhes_Vaga_FreeActivity

class Feed_FreeActivity : AbstractActivity() {

    private lateinit var feedFreeAdapter: Feed_FreelancerAdapter
    private val vagaList = mutableListOf<Vaga>() // Lista filtrada para exibição
    private val originalList = mutableListOf<Vaga>() // Lista original sem filtros


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_free)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()


        // Configurar RecyclerView e Adapter
        feedFreeAdapter = Feed_FreelancerAdapter(this, vagaList)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_feed_free)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = feedFreeAdapter

        // Configurar Spinner
        val spinner = findViewById<Spinner>(R.id.spinner_filtro_free)
        val filtros = listOf("Todos", "R$0 - R$1000", "R$1000 - R$5000", "R$5000+")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filtros)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val filtroSelecionado = filtros[position]
                aplicarFiltro(filtroSelecionado)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Carregar dados do Firestore
        loadVagasFreeFromFirestore()
        Log.d("Lista","$vagaList" )
    }

    private fun loadVagasFreeFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("vagas").get().addOnSuccessListener { snapshots ->
            originalList.clear() // Limpa os dados anteriores
            snapshots.forEach { document ->
                val idVaga = document.getString("idVaga") ?: ""
                val nomeProjeto = document.getString("nomeProjeto") ?: ""
                val descricao = document.getString("descricao") ?: ""
                val habilidades = document.getString("habilidades") ?: ""
                val email = document.getString("email") ?: ""
                val valorPago = document.getString("valorPago") ?: ""

                // Adiciona os itens na lista original
                originalList.add(
                    Vaga(
                        idVaga = idVaga,
                        idUser = document.getString("idUsuario") ?: "",
                        nomeProjeto = nomeProjeto,
                        descricao = descricao,
                        habilidades = habilidades,
                        email = email,
                        valorPago = valorPago
                    )
                )
            }

            vagaList.clear()
            vagaList.addAll(originalList) // Exibe tudo inicialmente
            feedFreeAdapter.notifyDataSetChanged()
        }
    }

    private fun aplicarFiltro(filtro: String) {
        vagaList.clear() // Limpa os itens atuais

        when (filtro) {
            "Todos" -> {
                vagaList.addAll(originalList) // Exibe todos os itens
            }
            "R$0 - R$1000" -> {
                vagaList.addAll(originalList.filter {
                    val valor = it.valorPago.toDoubleOrNull() ?: 0.0
                    valor in 0.0..1000.0
                })
            }
            "R$1000 - R$5000" -> {
                vagaList.addAll(originalList.filter {
                    val valor = it.valorPago.toDoubleOrNull() ?: 0.0
                    valor in 1000.0..5000.0
                })
            }
            "R$5000+" -> {
                vagaList.addAll(originalList.filter {
                    val valor = it.valorPago.toDoubleOrNull() ?: 0.0
                    valor > 5000.0
                })
            }
        }

        feedFreeAdapter.notifyDataSetChanged() // Atualiza a RecyclerView
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
}
