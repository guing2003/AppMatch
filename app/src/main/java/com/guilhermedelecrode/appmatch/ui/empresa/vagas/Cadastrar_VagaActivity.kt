package com.guilhermedelecrode.appmatch.ui.empresa.vagas

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.R
import java.util.UUID

class Cadastrar_VagaActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastrar_vaga)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = Color.parseColor("#00537D")
        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        // Habilitar suporte a ActionBar personalizada
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Definir o layout customizado na ActionBar
        val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
        supportActionBar?.customView = actionBarLayout
        supportActionBar?.setDisplayShowCustomEnabled(true)

        // Encontrar o botão no layout customizado da ActionBar
        val backButton = actionBarLayout.findViewById<Button>(R.id.action_bar_button)

        // Definir ação para o botão Voltar
        backButton.setOnClickListener {
            // Voltar à tela anterior ou realizar alguma ação
            onBackPressed()
        }

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