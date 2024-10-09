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
import com.guilhermedelecrode.appmatch.MainActivity
import com.guilhermedelecrode.appmatch.R

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

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                createUserWithEmailAndPassword(email, senha)
            } else {
                Toast.makeText(requireContext(), "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
    private fun createUserWithEmailAndPassword(email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmailAndPassword: Success")
                val user = auth.currentUser
                // Iniciando a Feed_EmpresaActivity após o sucesso da autenticação
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
    companion object {
        private var TAG  ="EmailAndSenha"
    }
}