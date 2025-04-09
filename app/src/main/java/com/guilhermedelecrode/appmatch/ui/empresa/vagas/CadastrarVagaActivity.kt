package com.guilhermedelecrode.appmatch.ui.empresa.vagas

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import java.util.UUID

class CadastrarVagaActivity() : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_vaga)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        val btn_cadastrar_vaga = findViewById<Button>(R.id.btn_cadastrar_vaga)

        btn_cadastrar_vaga.setOnClickListener {
            val nomeProjeto = findViewById<EditText>(R.id.edit_nome_projeto).text.toString()
            val descricao = findViewById<EditText>(R.id.edit_descricao_vaga).text.toString()
            val habilidades = findViewById<EditText>(R.id.edit_habilidades).text.toString()
            val email = findViewById<EditText>(R.id.edit_email_vaga).text.toString()
            val valorPago = findViewById<EditText>(R.id.edit_valor_pago).text.toString()

            if (nomeProjeto.isNotEmpty() && descricao.isNotEmpty() && habilidades.isNotEmpty() && email.isNotEmpty() && valorPago.isNotEmpty()) {
                val idVaga = UUID.randomUUID().toString()  // Cria um ID único para a vaga
                addVagas(idVaga, nomeProjeto, descricao, habilidades, email, valorPago)
                finish()
            } else {
                Toast.makeText(
                    this,  // Corrigido para `this` em uma Activity
                    "Por favor, preencha os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun addVagas(
        idVaga: String,
        nomeProjeto: String,
        descricao: String,
        habilidades: String,
        email: String,
        valorPago: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            val uidUser = it.uid

            // Cria um mapa com os dados da vaga
            val vaga = hashMapOf(
                "idVaga" to idVaga,
                "nomeProjeto" to nomeProjeto,
                "descricao" to descricao,
                "habilidades" to habilidades,
                "email" to email,
                "valorPago" to valorPago,
                "idUser" to uidUser  // Associa o UID do usuário logado com a vaga
            )

            // Adiciona o documento ao Firestore com o ID da vaga
            db.collection("vagas").document(idVaga)
                .set(vaga)
                .addOnSuccessListener {
                    Log.d("Firebase", "Documento adicionado com sucesso!")
                    Toast.makeText(this, "Vaga cadastrada com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnFailureListener { e ->
                    Log.w("Firebase", "Erro ao adicionar documento", e)
                    Toast.makeText(this, "Erro ao cadastrar vaga", Toast.LENGTH_SHORT).show()
                }
        }
    }
}