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

        configActionBarGeral()
        onResume()

        val btn_cadastrar_vaga = findViewById<Button>(R.id.btn_cadastrar_vaga_cadastrar_vaga_empresa)

        btn_cadastrar_vaga.setOnClickListener {
            val nomeProjeto = findViewById<EditText>(R.id.edit_nome_vaga_cadastrar_vaga_empresa).text.toString()
            val descricao = findViewById<EditText>(R.id.edit_descricao_vaga_cadastrar_vaga_empresa).text.toString()
            val habilidades = findViewById<EditText>(R.id.edit_habilidades_vaga_cadastrar_vaga_empresa).text.toString()
            val email = findViewById<EditText>(R.id.edit_email_vaga_cadastrar_vaga_empresa).text.toString()
            val valorPago = findViewById<EditText>(R.id.edit_valor_pago_cadastrar_vaga_empresa).text.toString()

            if (nomeProjeto.isNotEmpty() && descricao.isNotEmpty() && habilidades.isNotEmpty() && email.isNotEmpty() && valorPago.isNotEmpty()) {
                val idVaga = UUID.randomUUID().toString()  // Cria um ID único para a vaga
                addVagas(idVaga, nomeProjeto, descricao, habilidades, email, valorPago)
                finish()
            } else {
                Toast.makeText(
                    this,
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

            val vaga = hashMapOf(
                "idVaga" to idVaga,
                "nomeProjeto" to nomeProjeto,
                "descricao" to descricao,
                "habilidades" to habilidades,
                "email" to email,
                "valorPago" to valorPago,
                "idUser" to uidUser
            )

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