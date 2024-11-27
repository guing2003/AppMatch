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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.adapter.freelancer.OrdensServicoFreelancerAdapter
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.Feed_FreeActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.Perfil_FreeActivity
import kotlin.math.log

class Ordem_Servico_FreeActivity : AbstractActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrdensServicoFreelancerAdapter
    private val ordensServico = mutableListOf<OrdemServico>()
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ordem_servico_free)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid ?: ""

        recyclerView = findViewById(R.id.rv_ordem_servico_free)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OrdensServicoFreelancerAdapter(this, ordensServico)
        recyclerView.adapter = adapter
        loadOrdensServico()

    }

    private fun loadOrdensServico() {
        val db = FirebaseFirestore.getInstance()
        db.collection("ordemServicos")
            .whereEqualTo("idUserFree", userId)
            .get().addOnSuccessListener { querySnapshot ->
                val ordens =
                    querySnapshot.documents.mapNotNull { it.toObject(OrdemServico::class.java) }
                ordensServico.addAll(ordens)
                adapter.notifyDataSetChanged()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Erro ao carregar ordens de serviço: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
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
}
