package com.guilhermedelecrode.appmatch.ui.empresa.ofertas

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
import com.guilhermedelecrode.appmatch.model.empresa.Ofertas
import com.guilhermedelecrode.appmatch.model.empresa.Vaga

class OfertasEmpresaActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ofertas_empresa)

        configActionBarGeral()
        onResume()

        val idVaga = intent.getStringExtra("idVaga").toString()
        loadVagaAndOffers(idVaga)
    }

    private fun loadVagaAndOffers(idVaga: String) {
        val db = FirebaseFirestore.getInstance()

        println("ID da Vaga: $idVaga")

        val vagaRef = db.collection("vagas").document(idVaga)
        vagaRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val vaga = documentSnapshot.toObject(Vaga::class.java)
                if (vaga != null) {
                    loadOffersForJob(idVaga, vaga)
                } else {
                    Toast.makeText(this, "Vaga não encontrada.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Documento da vaga não encontrado.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Erro ao carregar vaga: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadOffersForJob(idVaga: String, vaga: Vaga) {
        val db = FirebaseFirestore.getInstance()
        db.collection("ofertas").whereEqualTo("idVaga", idVaga).get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("Ofertas_Enviadas_EmpresaActivity", "Ofertas: ${querySnapshot.documents}")
                val offers = querySnapshot.documents.mapNotNull { it.toObject(Ofertas::class.java) }
                updateOffersUI(offers, vaga)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar ofertas: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateOffersUI(offers: List<Ofertas>, vaga: Vaga) {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_ofertas_empresa_activity)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OfertasEmpresaAdapter(this, offers, vaga, ::onStatusChange)
    }

    private fun onStatusChange(oferta: Ofertas, newStatus: String) {
        val db = FirebaseFirestore.getInstance()
        val ofertaRef = db.collection("ofertas").document(oferta.idOferta)

        Log.d("Ofertas_Enviadas_EmpresaActivity", "Atualizando status da oferta: ${oferta.idOferta} para $newStatus")

        ofertaRef.update("status", newStatus)
            .addOnSuccessListener {
                Toast.makeText(this, "Status atualizado para $newStatus", Toast.LENGTH_SHORT).show()
                Log.d("Ofertas_Enviadas_EmpresaActivity", "Status atualizado com sucesso para $newStatus")
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao atualizar status: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("Ofertas_Enviadas_EmpresaActivity", "Erro ao atualizar status: ${e.message}")
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_ofertas_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /*R.id.perfil -> {
                Intent(this, Vagas_Cadastradas_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }*/
        }
        return super.onOptionsItemSelected(item)
    }
}
