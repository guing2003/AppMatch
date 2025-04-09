package com.guilhermedelecrode.appmatch.ui.empresa.perfil

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
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.PerfilEmpresa
import com.guilhermedelecrode.appmatch.ui.empresa.feed.FeedEmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.feed.FeedEmpresaActivity.Companion.VAGA
import com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico.OrdemServicoEmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.CadastrarVagaActivity

class PerfilEmpresaActivity : AbstractActivity() {
    private lateinit var perfilAdapter: PerfilEmpresaAdapter
    private var perfilListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_empresa)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        // Inicializar RecyclerView e adapter
        val perfilList = mutableListOf<PerfilEmpresa>()
        perfilAdapter = PerfilEmpresaAdapter(perfilList, this)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_perfil_empresa)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = perfilAdapter

        // Carregar vagas do Firestore
        loadPerfilFromFirestore()
        Log.d("Lista","$perfilList" )
        onResume()
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

                    val perfil_empresa = mutableListOf<PerfilEmpresa>()
                    snapshots?.forEach { document ->
                        val nome_empresa = document.getString("nomeEmpresa") ?: ""
                        val cnpj = document.getString("cnpj") ?: ""
                        val endereco = document.getString("endereco") ?: ""
                        val numero = document.getString("numero") ?: ""
                        val telefone = document.getString("telefone") ?: ""
                        val email = document.getString("email") ?: ""
                        val seguimento = document.getString("seguimento") ?: ""
                        val perfil = PerfilEmpresa(nome_empresa, cnpj, endereco,numero, telefone , email, seguimento)
                        perfil_empresa.add(perfil)
                    }

                    Log.d("Perfil", "Lista de perfil atualizadas em tempo real: $perfil_empresa")
                    perfilAdapter.updateData(perfil_empresa) // Atualiza o adapter com os dados em tempo real
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
        menuInflater.inflate(R.menu.menu_item_perfil_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.perfilPaginaInicial -> {
                Intent(this, FeedEmpresaActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            R.id.perfilNovoServico -> {
                Intent(this, CadastrarVagaActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            R.id.perfilOrdemServicoEmpresa -> {
                Intent(this, OrdemServicoEmpresaActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
            R.id.perfilSair -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)

    }
}