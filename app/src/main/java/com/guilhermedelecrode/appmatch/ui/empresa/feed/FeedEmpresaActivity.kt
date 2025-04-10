package com.guilhermedelecrode.appmatch.ui.empresa.feed

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico.OrdemServicoEmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.PerfilEmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.CadastrarVagaActivity

class FeedEmpresaActivity : AbstractActivity() {
    private lateinit var feedAdapter: FeedEmpresaAdapter
    private var vagasListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_empresa)

        configActionBarGeral()
        onResume()

        val vagaList = mutableListOf<Vaga>()
        feedAdapter = FeedEmpresaAdapter(this, vagaList)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_feed_empresa)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = feedAdapter

        val searchEditText = findViewById<EditText>(R.id.edit_pesquisar_vagas_feed_empresa)
        val searchButton = findViewById<Button>(R.id.btn_pesquisar_vagas_feed_empresa)

        searchButton.setOnClickListener {
            val queryText = searchEditText.text.toString().trim()
            if (queryText.isNotEmpty()) {
                searchVagas(queryText)
            } else {
                Toast.makeText(this, "Digite algo para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        loadVagasFromFirestore()
        Log.d("Lista","$vagaList" )
    }

    private fun searchVagas(queryText: String) {
        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        if (idUser != null) {
            db.collection("vagas")
                .whereEqualTo("idUser", idUser)
                .get()
                .addOnSuccessListener { snapshots ->
                    val vagas = mutableListOf<Vaga>()
                    snapshots?.forEach { document ->
                        val nomeProjeto = document.getString("nomeProjeto") ?: ""
                        val habilidades = document.getString("habilidades") ?: ""

                        // Verificar se a consulta corresponde ao nome do projeto ou às habilidades
                        if (nomeProjeto.contains(queryText, ignoreCase = true) ||
                            habilidades.contains(queryText, ignoreCase = true)
                        ) {
                            val vaga = Vaga(
                                idVaga = document.getString("idVaga") ?: "",
                                idUser = document.getString("idUser") ?: "",
                                id = document.getString("id") ?: "",
                                nomeEmpresa = document.getString("nomeEmpresa") ?: "",
                                nomeProjeto = nomeProjeto,
                                descricao = document.getString("descricao") ?: "",
                                habilidades = habilidades,
                                email = document.getString("email") ?: "",
                                valorPago = document.getString("valorPago") ?: ""
                            )
                            vagas.add(vaga)
                        }
                    }
                    feedAdapter.updateData(vagas)
                }
                .addOnFailureListener { error ->
                    Log.e(VAGA, "Erro ao buscar vagas: ${error.message}")
                    Toast.makeText(this, "Erro ao buscar vagas", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadVagasFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        if (idUser != null) {
            vagasListener = db.collection("vagas")
                .whereEqualTo("idUser", idUser)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.d(VAGA, "Erro ao carregar dados: ${error.message}")
                        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    val vagas = mutableListOf<Vaga>()
                    snapshots?.forEach { document ->
                        val idVaga = document.getString("idVaga") ?: ""
                        val idUser = document.getString("idUser") ?: ""
                        val id = document.getString("id") ?: ""
                        val nomeProjeto = document.getString("nomeProjeto") ?: ""
                        val nomeEmpresa = document.getString("nomeEmpresa") ?: ""
                        val descricao = document.getString("descricao") ?: ""
                        val habilidades = document.getString("habilidades") ?: ""
                        val email = document.getString("email") ?: ""
                        val valorPago = document.getString("valorPago") ?: ""

                        Log.d(VAGA, "ID Vaga: $idVaga")
                        Log.d(VAGA, "ID User: $idUser")
                        Log.d(VAGA, "ID Freelancer: $id")
                        Log.d(VAGA, "Nome Projeto: $nomeProjeto")
                        Log.d(VAGA, "Nome Empresa: $nomeEmpresa")
                        Log.d(VAGA, "Descrição: $descricao")
                        Log.d(VAGA, "Habilidades: $habilidades")
                        Log.d(VAGA, "Email: $email")
                        Log.d(VAGA, "Valor Pago: $valorPago")

                        val vaga = Vaga(idVaga, idUser, id, nomeEmpresa, nomeProjeto, descricao, habilidades, email, valorPago)
                        vagas.add(vaga)
                    }

                    Log.d("Vagas", "Lista de vagas atualizadas em tempo real: $vagas")
                    feedAdapter.updateData(vagas)
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        vagasListener?.remove()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_feed_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.feedPerfilEmpresa -> {
                startActivity(Intent(this, PerfilEmpresaActivity::class.java))
            }

            R.id.feedNovoServicoEmpresa -> {
                startActivity(Intent(this, CadastrarVagaActivity::class.java))
            }

            R.id.feedOrdemServicoEmpresa -> {
                startActivity(Intent(this, OrdemServicoEmpresaActivity::class.java))
            }

            R.id.feedSair -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

        companion object {
            const val VAGA = "CarregarVaga"
        }
    }
