package com.guilhermedelecrode.appmatch.ui.empresa.perfil

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

class EditarPerfilEmpresaActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil_empresa)

        configActionBarGeral()
        onResume()

        loadEditPerfilFromFirestore()

        val btn_salvar_edit_empresa = findViewById<Button>(R.id.btn_salvar_edit_empresa_activity)
        btn_salvar_edit_empresa.setOnClickListener{
            updatePerfilToFirestore()
            finish()
            val intent = Intent(this, PerfilEmpresaActivity::class.java)
            startActivity(intent)
        }

        val btn_cancelar_edit_empresa = findViewById<Button>(R.id.btn_cancelar_edit_empresa_activity)
        btn_cancelar_edit_empresa.setOnClickListener{
            val intent = Intent(this, PerfilEmpresaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private var perfilListener: ListenerRegistration? = null

    private fun loadEditPerfilFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        val edit_endereco_empresa : EditText = findViewById(R.id.edit_endereco_empresa_activity)
        val edit_numero_empresa : EditText = findViewById(R.id.edit_numero_empresa_activity)
        val edit_telefone_empresa : EditText = findViewById(R.id.edit_telefone_empresa_activity)

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
                        val nome_empresa = document.getString("nomeEmpresa") ?: ""
                        val endereco = document.getString("endereco") ?: ""
                        val numero = document.getString("numero") ?: ""
                        val telefone = document.getString("telefone") ?: ""

                        edit_endereco_empresa.setText(endereco)
                        edit_numero_empresa.setText(numero)
                        edit_telefone_empresa.setText(telefone)

                        Log.d("Perfil", "Perfil carregado: Nome da Empresa: $nome_empresa")
                    }
                }
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePerfilToFirestore() {

        val edit_endereco_empresa : EditText = findViewById(R.id.edit_endereco_empresa_activity)
        val edit_numero_empresa : EditText = findViewById(R.id.edit_numero_empresa_activity)
        val edit_telefone_empresa : EditText = findViewById(R.id.edit_telefone_empresa_activity)


        val updatedEndereco = edit_endereco_empresa.text.toString()
        val updatedNumero = edit_numero_empresa.text.toString()
        val updatedTelefone = edit_telefone_empresa.text.toString()


        val db = FirebaseFirestore.getInstance()
        val idUser = FirebaseAuth.getInstance().currentUser?.uid

        if (idUser != null) {
            val userProfileRef = db.collection("usuarios").document(idUser)

            val updatedProfile = mapOf(
                "endereco" to updatedEndereco,
                "numero" to updatedNumero,
                "telefone" to updatedTelefone
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