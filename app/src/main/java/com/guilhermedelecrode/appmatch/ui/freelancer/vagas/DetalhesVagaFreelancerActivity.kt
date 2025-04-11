package com.guilhermedelecrode.appmatch.ui.freelancer.vagas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.FeedFreelancerActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico.OrdemServicoFreelancerActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.PerfilFreelancerActivity

class DetalhesVagaFreelancerActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_vaga_freelancer)

        configActionBarGeral()
        onResume()

        val idVaga = intent.getStringExtra("idVaga") ?: return

        carregarDetalhesVaga(idVaga)
    }

    private fun carregarDetalhesVaga(idVaga: String) {
        val vagasRef = FirebaseFirestore.getInstance().collection("vagas")
        val usuariosRef = FirebaseFirestore.getInstance().collection("usuarios")

        vagasRef.document(idVaga).get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val vaga = documentSnapshot.toObject(Vaga::class.java)

                if (vaga != null) {
                    val idUser = vaga.idUser
                    usuariosRef.document(idUser).get().addOnSuccessListener { usuarioSnapshot ->
                        val nomeEmpresa = usuarioSnapshot.getString("nomeEmpresa") ?: "Empresa Desconhecida"

                        vaga.nomeEmpresa = nomeEmpresa

                        Log.d("DetalhesVaga", "IdUser: $idUser")
                        Log.d("DetalhesVaga", "Nome da empresa carregado: $nomeEmpresa")
                        Log.d("DetalhesVaga", "UsuarioSnapshot Data: ${usuarioSnapshot.data}")

                        val recyclerView = findViewById<RecyclerView>(R.id.rv_descricao_vaga_detalhes_vaga_freelancer_activity)
                        recyclerView.layoutManager = LinearLayoutManager(this)
                        recyclerView.adapter = DetalhesVagaFreelancerAdapter(this, mutableListOf(vaga))
                    }.addOnFailureListener {
                        Toast.makeText(this, "Erro ao carregar informações da empresa.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Detalhes da vaga não encontrados.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Erro ao carregar detalhes: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_detalhes_vagas_free, menu)
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

            R.id.ordemServicoFree -> {
                Intent(this, OrdemServicoFreelancerActivity::class.java).apply {
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
            R.id.sairFree -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
