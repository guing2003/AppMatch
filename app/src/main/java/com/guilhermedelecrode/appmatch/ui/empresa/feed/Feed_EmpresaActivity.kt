package com.guilhermedelecrode.appmatch.ui.empresa.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
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
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico.Ordem_Servico_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.Perfil_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.Cadastrar_VagaActivity
class Feed_EmpresaActivity : AbstractActivity() {
    private lateinit var feedAdapter: Feed_EmpresaAdapter
    private var vagasListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_empresa)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        // Inicializar RecyclerView e adapter
        val vagaList = mutableListOf<Vaga>()
        feedAdapter = Feed_EmpresaAdapter(this, vagaList)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_feed)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = feedAdapter

        // Carregar vagas do Firestore
        loadVagasFromFirestore()
        Log.d("Lista","$vagaList" )
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
        // Remove o listener para evitar vazamento de memória
        vagasListener?.remove()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_feed_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.feedPerfilEmpresa -> {
                startActivity(Intent(this, Perfil_EmpresaActivity::class.java))
            }

            R.id.feedNovoServicoEmpresa -> {
                startActivity(Intent(this, Cadastrar_VagaActivity::class.java))
            }

            R.id.feedOrdemServicoEmpresa -> {
                startActivity(Intent(this, Ordem_Servico_EmpresaActivity::class.java))
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
