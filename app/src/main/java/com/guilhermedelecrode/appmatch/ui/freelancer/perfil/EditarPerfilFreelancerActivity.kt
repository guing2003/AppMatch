package com.guilhermedelecrode.appmatch.ui.freelancer.perfil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.empresa.feed.FeedEmpresaActivity.Companion.VAGA

class EditarPerfilFreelancerActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil_freelancer)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()
        loadEditPerfilFromFirestore()


        val btn_cancelar_edit_free = findViewById<Button>(R.id.btn_cancelar_edit_empresa)
        val btn_salvar_edit_free = findViewById<Button>(R.id.btn_salvar_edit_empresa)

        btn_salvar_edit_free.setOnClickListener {
            updatePerfilToFirestore()
            val intent = Intent(this, PerfilFreelancerActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_cancelar_edit_free.setOnClickListener {
            val intent = Intent(this, PerfilFreelancerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private var perfilListener: ListenerRegistration? = null

    private fun loadEditPerfilFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        val edit_endereco_free : EditText = findViewById(R.id.edit_endereco_free)
        val edit_numero_free : EditText = findViewById(R.id.edit_numero_free)
        val edit_telefone_free : EditText = findViewById(R.id.edit_telefone_free)
        val edit_descricao_free : EditText = findViewById(R.id.edit_descricao)

        if (idUser != null) {
            perfilListener = db.collection("usuarios")
                .whereEqualTo("id", idUser)
                .addSnapshotListener { snapshots, error ->
                    if (error != null) {
                        Log.d(VAGA, "Erro ao carregar dados: ${error.message}")
                        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    snapshots?.forEach { document ->
                        val nome_free = document.getString("nomeEmpresa") ?: ""
                        val endereco = document.getString("endereco") ?: ""
                        val numero = document.getString("numero") ?: ""
                        val telefone = document.getString("telefone") ?: ""
                        val descricao = document.getString("descricao") ?: ""

                        // Preencher os campos de EditText diretamente com os dados recuperados
                        edit_endereco_free.setText(endereco)
                        edit_numero_free.setText(numero)
                        edit_telefone_free.setText(telefone)
                        edit_descricao_free.setText(descricao)

                        Log.d("Perfil", "Perfil carregado: Nome da Empresa: $nome_free")
                    }
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePerfilToFirestore() {

        val edit_endereco_free : EditText = findViewById(R.id.edit_endereco_free)
        val edit_numero_free : EditText = findViewById(R.id.edit_numero_free)
        val edit_telefone_free : EditText = findViewById(R.id.edit_telefone_free)
        val edit_descricao : EditText = findViewById(R.id.edit_descricao)

        val updatedEndereco = edit_endereco_free.text.toString()
        val updatedNumero = edit_numero_free.text.toString()
        val updatedTelefone = edit_telefone_free.text.toString()
        val updateDescricao = edit_descricao.text.toString()


        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        if (idUser != null) {
            val userProfileRef = db.collection("usuarios").document(idUser)

            val updatedProfile = mapOf(
                "endereco" to updatedEndereco,
                "numero" to updatedNumero,
                "telefone" to updatedTelefone,
                "descricao" to updateDescricao
            )

            userProfileRef.update(updatedProfile)
                .addOnSuccessListener {
                    Toast.makeText(this, "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao atualizar perfil: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}