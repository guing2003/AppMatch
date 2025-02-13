package com.guilhermedelecrode.appmatch.ui.cadastro.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.MainActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.cadastro.fragment.Cadastro_EmpresaFragment.Companion.DB

class Cadastro_FreeFragment: Fragment(){

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastro_free, container, false)
        // Inicializando FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val btn_cadastro_free : Button = view.findViewById(R.id.btn_cadastro_free)

        btn_cadastro_free.setOnClickListener {
            // Obtendo os valores dos campos EditText corretamente
            val email: String = view.findViewById<EditText>(R.id.edit_email_free).text.toString()
            val senha: String = view.findViewById<EditText>(R.id.edit_senha_free).text.toString()
            val nome: String = view.findViewById<EditText>(R.id.edit_nome_free).text.toString()
            val cpf: String = view.findViewById<EditText>(R.id.edit_cpf).text.toString()
            val endereco : String = view.findViewById<EditText>(R.id.edit_endereco_free).text.toString()
            val numero : String = view.findViewById<EditText>(R.id.edit_numero_free).text.toString()
            val telefone: String = view.findViewById<EditText>(R.id.edit_telefone_free).text.toString()
            val tipoUsuario: String = "freelancer"

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                createUserWithEmailAndPassword(email, senha, nome, cpf,endereco, numero, telefone, tipoUsuario)
            } else {
                Toast.makeText(requireContext(), "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    private fun createUserWithEmailAndPassword(email: String, senha: String, nome: String, cpf: String, endereco: String, numero : String, telefone: String,  tipoUsuario: String) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmailAndPassword: Success")
                val user = auth.currentUser
                user?.let{
                    saveUserData(it.uid,email, nome, cpf,endereco, numero, telefone, tipoUsuario )
                }
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                // Você pode adicionar finish() aqui se quiser fechar a activity atual
                // finish()
            } else {
                val exception = task.exception
                if (exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(), "E-mail inválido. Verifique o formato.", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "createUserWithEmailAndPassword: Failure", exception)
                    Toast.makeText(requireContext(), "Erro na autenticação", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveUserData(uid: String, email: String,nome:String, cpf: String, endereco: String, numero: String, telefone: String, tipoUsuario: String) {
        // Cria um mapa com os dados que serão salvos no Firestore
        val userData = hashMapOf(
            "id" to uid,
            "email" to email,
            "nome" to nome,
            "cpf" to cpf,
            "endereco" to endereco,
            "numero" to numero,
            "telefone" to telefone,
            "tipoUsuario" to tipoUsuario
        )

        // Salvando os dados no Firestore usando o UID como o ID do documento
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
        var TAG  ="EmailAndSenha"
        var DB = "BancoDeDados"
    }
}