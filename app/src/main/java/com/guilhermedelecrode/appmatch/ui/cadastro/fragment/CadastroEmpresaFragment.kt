package com.guilhermedelecrode.appmatch.ui.cadastro.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.MainActivity
import com.guilhermedelecrode.appmatch.R

class CadastroEmpresaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_cadastro_empresa, container, false)

        auth = FirebaseAuth.getInstance()

        val btn_cadastro_empresa: Button = view.findViewById(R.id.btn_cadastro_empresa_fragment)

        btn_cadastro_empresa.setOnClickListener {
            val email: String = view.findViewById<EditText>(R.id.edit_email_cadastro_empresa_fragment).text.toString()
            val senha: String = view.findViewById<EditText>(R.id.edit_senha_cadastro_empresa_fragment).text.toString()
            val nome: String = view.findViewById<EditText>(R.id.edit_nome_cadastro_empresa_fragment).text.toString()
            val cnpj: String = view.findViewById<EditText>(R.id.edit_cnpj_cadastro_empresa_fragment).text.toString()
            val telefone: String =
                view.findViewById<EditText>(R.id.edit_telefone_cadastro_empresa_fragment).text.toString()
            val endereco: String =
                view.findViewById<EditText>(R.id.edit_endereco_cadastro_empresa_fragment).text.toString()
            val tipoUsuario: String = "empresa"

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                createUserWithEmailAndPassword(
                    email,
                    senha,
                    tipoUsuario,
                    nome,
                    cnpj,
                    telefone,
                    endereco
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, preencha os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return view
    }

    private fun createUserWithEmailAndPassword(
        email: String,
        senha: String,
        tipoUsuario: String,
        nome: String,
        cnpj: String,
        telefone: String,
        endereco: String
    ) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmailAndPassword: Success")
                val user = auth.currentUser
                user?.let {
                    saveUserData(it.uid, email, tipoUsuario, nome, cnpj, telefone, endereco)
                }
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            } else {
                val exception = task.exception
                if (exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(
                        requireContext(),
                        "E-mail inválido. Verifique o formato.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.d(TAG, "createUserWithEmailAndPassword: Failure", exception)
                    Toast.makeText(requireContext(), "Erro na autenticação", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun saveUserData(
        uid: String,
        email: String,
        tipoUsuario: String,
        nome: String,
        cnpj: String,
        telefone: String,
        endereco: String
    ) {
        val userData = hashMapOf(
            "id" to uid,
            "email" to email,
            "nomeEmpresa" to nome,
            "cnpj" to cnpj,
            "telefone" to telefone,
            "endereco" to endereco,
            "tipoUsuario" to tipoUsuario
        )

        val db = FirebaseFirestore.getInstance()
        db.collection("usuarios").document(uid)
            .set(userData)
            .addOnSuccessListener {
                Log.d(DB, "Dados do usuário salvos no Firestore com sucesso.")
            }
            .addOnFailureListener { e ->
                Log.w(DB, "Erro ao salvar os dados do usuário no Firestore", e)
            }
    }

    companion object {
        var TAG = "EmailAndSenha"
        var DB = "BancoDeDados"
    }
}