package com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.FeedFreelancerActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.PerfilFreelancerActivity

class OrdemServicoFreelancerActivity : AbstractActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrdensServicoFreelancerAdapter
    private val ordensServico = mutableListOf<OrdemServico>()
    private val ordensServicoOriginal = mutableListOf<OrdemServico>() // Lista original
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordem_servico_freelancer)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show()
            finish() // Finaliza a activity ou redireciona para login
            return
        } else {
            userId = currentUser.uid
        }

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.rv_ordem_servico_free)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OrdensServicoFreelancerAdapter(this, ordensServico)
        recyclerView.adapter = adapter

        // Carregar as ordens de serviço
        loadOrdensServico()

        // Inicializar Spinner para filtrar por status
        val spinner = findViewById<Spinner>(R.id.spinner_status_ordem_free)
        val statusOptions = listOf("Todos", "Em andamento", "Concluída")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        // Ação ao selecionar um item do Spinner
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val statusSelecionado = statusOptions[position]
                aplicarFiltro(statusSelecionado)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Nada acontece se nenhum item for selecionado
            }
        })

        // Definir valor padrão para o spinner
        spinner.setSelection(0) // Seleciona o primeiro item, que no caso seria "Todos"
    }

    private fun loadOrdensServico() {
        val db = FirebaseFirestore.getInstance()
        db.collection("ordemServicos")
            .whereEqualTo("idUserFree", userId)
            .get().addOnSuccessListener { querySnapshot ->
                val ordens = querySnapshot.documents.mapNotNull { it.toObject(OrdemServico::class.java) }
                ordensServicoOriginal.clear()
                ordensServicoOriginal.addAll(ordens) // Salva a lista original
                ordensServico.clear()
                ordensServico.addAll(ordens) // Inicializa com todas as ordens
                adapter.notifyDataSetChanged()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Erro ao carregar ordens de serviço: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun aplicarFiltro(status: String) {
        if (ordensServicoOriginal.isEmpty()) return // Evita processamento se não houver ordens

        // Converte o status para minúsculas para garantir que a comparação seja case-insensitive
        val statusLower = status.lowercase()

        val ordensFiltradas = when (statusLower) {
            "todos" -> ordensServicoOriginal // Retorna todas as ordens
            "em andamento" -> ordensServicoOriginal.filter { it.status?.lowercase() == "em andamento" }
            "concluída" -> ordensServicoOriginal.filter { it.status?.lowercase() == "concluída" }
            else -> ordensServicoOriginal
        }

        // Atualiza a lista no Adapter
        adapter.updateOrdensServico(ordensFiltradas)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_ordem_free, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilFree -> {
                Intent(this, PerfilFreelancerActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            R.id.paginaInicial -> {
                Intent(this, FeedFreelancerActivity::class.java).apply {
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
