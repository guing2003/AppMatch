package com.guilhermedelecrode.appmatch.ui.freelancer.perfil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.model.freelancer.PerfilFreelancer
import com.guilhermedelecrode.appmatch.ui.empresa.feed.FeedEmpresaActivity.Companion.VAGA
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.FeedFreelancerActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico.OrdemServicoFreelancerActivity

class PerfilFreelancerActivity : AbstractActivity() {

    private lateinit var perfilAdapter: PerfilFreelancerAdapter
    private var perfilListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_freelancer)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        // Inicializar RecyclerView e adapter
        val perfilList = mutableListOf<PerfilFreelancer>()
        perfilAdapter = PerfilFreelancerAdapter(perfilList, this)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_perfil_free)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = perfilAdapter

        // Carregar vagas do Firestore
        loadPerfilFromFirestore()
        Log.d("Lista","$perfilList" )
    }

    private fun loadPerfilFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        if (idUser != null) {
            perfilListener = db.collection("usuarios")
                .whereEqualTo("id", idUser)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.d(VAGA, "Erro ao carregar dados: ${error.message}")
                        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    val perfil_free = mutableListOf<PerfilFreelancer>()
                    snapshots?.forEach { document ->
                        val nome_free = document.getString("nome") ?: ""
                        val cpf = document.getString("cpf") ?: ""
                        val endereco = document.getString("endereco") ?: ""
                        val numero = document.getString("numero") ?: ""
                        val telefone = document.getString("telefone") ?: ""
                        val email = document.getString("email") ?: ""
                        val descricao = document.getString("descricao") ?: ""
                        val perfil = PerfilFreelancer(nome_free, cpf, endereco,numero, telefone , email, descricao)
                        perfil_free.add(perfil)
                    }

                    Log.d("Perfil", "Lista de perfil atualizadas em tempo real: $perfil_free")
                    perfilAdapter.updateData(perfil_free) // Atualiza o adapter com os dados em tempo real
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove o listener para evitar vazamento de memória
        perfilListener?.remove()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_perfil_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.paginaInicial -> {
                Intent(this, FeedFreelancerActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            R.id.ordemServicoFree -> {
                Intent(this,OrdemServicoFreelancerActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            R.id.sair -> {
                finishAffinity()            }
        }
        return super.onOptionsItemSelected(item)

    }
}