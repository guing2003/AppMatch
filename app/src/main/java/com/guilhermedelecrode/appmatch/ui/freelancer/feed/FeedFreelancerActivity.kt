package com.guilhermedelecrode.appmatch.ui.freelancer.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico.OrdemServicoFreelancerActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.PerfilFreelancerActivity

class FeedFreelancerActivity : AbstractActivity() {

    private lateinit var feedFreeAdapter: FeedFreelancerAdapter
    private val vagaList = mutableListOf<Vaga>()
    private val originalList = mutableListOf<Vaga>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_freelancer)

        configActionBarGeral()
        onResume()


        feedFreeAdapter = FeedFreelancerAdapter(this, vagaList)
        val recyclerView = findViewById<RecyclerView>(R.id.rv_feed_freelancer_activity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = feedFreeAdapter


        val etBuscarHabilidade = findViewById<EditText>(R.id.edit_buscar_vaga_habilidade_feed_freelancer_activity)
        val btnBuscarHabilidade = findViewById<Button>(R.id.btn_buscar_vaga_habilidade_feed_freelancer_activity)

        btnBuscarHabilidade.setOnClickListener {
            val query = etBuscarHabilidade.text.toString().trim()
            if (query.isNotEmpty()) {
                buscarPorHabilidade(query)
            } else {
                Toast.makeText(this, "Por favor, digite uma habilidade para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        loadVagasFreeFromFirestore()
        Log.d("Lista","$vagaList" )
    }


    private fun buscarPorHabilidade(habilidade: String) {
        vagaList.clear()
        vagaList.addAll(originalList.filter {
            it.habilidades.contains(habilidade, ignoreCase = true)
        })

        if (vagaList.isEmpty()) {
            Toast.makeText(this, "Nenhuma vaga encontrada para a habilidade: $habilidade", Toast.LENGTH_SHORT).show()
        }

        feedFreeAdapter.notifyDataSetChanged()
    }

    private fun loadVagasFreeFromFirestore() {
        val db = FirebaseFirestore.getInstance()

        db.collection("vagas").get().addOnSuccessListener { snapshots ->
            originalList.clear()
            snapshots.forEach { document ->
                val idVaga = document.getString("idVaga") ?: ""
                val nomeProjeto = document.getString("nomeProjeto") ?: ""
                val descricao = document.getString("descricao") ?: ""
                val habilidades = document.getString("habilidades") ?: ""
                val email = document.getString("email") ?: ""
                val valorPago = document.getString("valorPago") ?: ""

                originalList.add(
                    Vaga(
                        idVaga = idVaga,
                        idUser = document.getString("idUsuario") ?: "",
                        id = "",
                        nomeProjeto = nomeProjeto,
                        nomeEmpresa = "",
                        descricao = descricao,
                        habilidades = habilidades,
                        email = email,
                        valorPago = valorPago
                    )
                )
            }

            vagaList.clear()
            vagaList.addAll(originalList) 
            feedFreeAdapter.notifyDataSetChanged()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_feed_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilFree -> {
                startActivity(Intent(this, PerfilFreelancerActivity::class.java))

            }
            R.id.ordemServicoFree -> {
                Intent(this, OrdemServicoFreelancerActivity::class.java).apply {
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
