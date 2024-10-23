package com.guilhermedelecrode.appmatch.ui.cadastro.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.MainActivity
import com.guilhermedelecrode.appmatch.R

class Cadastro_EmpresaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val view = inflater.inflate(R.layout.fragment_cadastro_empresa, container, false)
        // Inicializando FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val btn_cadastro_empresa: Button = view.findViewById(R.id.btn_cadastro_empresa)

        btn_cadastro_empresa.setOnClickListener {
            // Obtendo os valores dos campos EditText corretamente
            val email: String = view.findViewById<EditText>(R.id.edit_email_empresa).text.toString()
            val senha: String = view.findViewById<EditText>(R.id.edit_senha_empresa).text.toString()
            val nome: String = view.findViewById<EditText>(R.id.edit_nome_empresa).text.toString()
            val cnpj: String = view.findViewById<EditText>(R.id.edit_cnpj).text.toString()
            val telefone: String = view.findViewById<EditText>(R.id.edit_telefone_empresa).text.toString()
            val endereco: String = view.findViewById<EditText>(R.id.edit_endereco_empresa).text.toString()
            val tipoUsuario: String = "empresa"

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                createUserWithEmailAndPassword(email, senha, tipoUsuario,nome , cnpj, telefone, endereco )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, preencha os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        /* val spinner: Spinner = view.findViewById(R.id.spinner)
        //Inicializando spinner
        val spinnerManager = SpinnerManager(requireContext())
        //Definindo os items
        val items = listOf("Automóveis", "Tecnologia", "Medicina", "Alimenticio")
        //Configurando o Spinner
        spinnerManager.setupSpinner(spinner, items)*/

        return view
    }

    private fun createUserWithEmailAndPassword(email: String, senha: String, tipoUsuario: String, nome:String, cnpj: String, telefone:String, endereco: String ) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmailAndPassword: Success")
                val user = auth.currentUser
                user?.let{
                    saveUserData(it.uid,email , tipoUsuario, nome, cnpj, telefone, endereco )
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
    private fun saveUserData(uid: String, email: String, tipoUsuario: String,nome:String,  cnpj: String, telefone: String, endereco: String) {
        // Cria um mapa com os dados que serão salvos no Firestore
        val userData = hashMapOf(
            "id" to uid,
            "email" to email,
            "nomeEmpresa" to nome,
            "cnpj" to cnpj,
            "telefone" to telefone,
            "endereco" to endereco,
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


    class SpinnerManager(private val context: Context) {
        fun setupSpinner(spinner: Spinner, items: List<String>) {
            val adapter =
                ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }
    }
}