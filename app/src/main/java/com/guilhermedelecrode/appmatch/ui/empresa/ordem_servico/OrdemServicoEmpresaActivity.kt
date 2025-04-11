package com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico
import com.guilhermedelecrode.appmatch.ui.empresa.feed.FeedEmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.PerfilEmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.CadastrarVagaActivity

class OrdemServicoEmpresaActivity : AbstractActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrdensServicoAdapter
    private val ordensServico = mutableListOf<OrdemServico>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordem_servico_empresa)

        recyclerView = findViewById(R.id.rv_ordem_servico_empresa_activity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OrdensServicoAdapter(this, ordensServico)
        recyclerView.adapter = adapter

        configActionBarGeral()
        onResume()
        loadOrdensServico()

        val spinner = findViewById<Spinner>(R.id.spinner_ordem_servico_empresa_activity)
        val statusOptions = listOf("Todos", "Em andamento", "Concluída")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedStatus = statusOptions[position]
                filterOrdensServico(selectedStatus)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Não é necessário implementar
            }
        }
    }

    private fun filterOrdensServico(status: String) {
        val filteredOrdens = if (status == "Todos") {
            ordensServico
        } else {
            ordensServico.filter { it.status == status }
        }
        adapter.updateData(filteredOrdens)
    }



    private fun loadOrdensServico() {
        val db = FirebaseFirestore.getInstance()
        db.collection("ordemServicos").get()
            .addOnSuccessListener { querySnapshot ->
                val ordens = querySnapshot.documents.mapNotNull { it.toObject(OrdemServico::class.java) }
                ordensServico.clear()
                ordensServico.addAll(ordens)
                adapter.updateData(ordens)
            }.addOnFailureListener { e ->
                // Lidar com o erro
            }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_ordem_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilEmpresa -> {
                Intent(this, PerfilEmpresaActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            R.id.paginaInicial -> {
                Intent(this, FeedEmpresaActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            R.id.novoServico -> {
                Intent(this, CadastrarVagaActivity::class.java).apply { //Criar esta tela
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