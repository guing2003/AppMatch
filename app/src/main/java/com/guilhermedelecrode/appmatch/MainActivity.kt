package com.guilhermedelecrode.appmatch


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.ui.cadastro.CadastroActivity
import com.guilhermedelecrode.appmatch.ui.empresa.feed.Feed_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.freelancer.feed.Feed_FreeActivity
import org.w3c.dom.Text

class MainActivity : AbstractActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar a ActionBar geral
        configActionBarLogin()

        auth = Firebase.auth

        val btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener {
            // Obtendo os valores dos campos EditText corretamente
            val email: String = findViewById<EditText>(R.id.edit_email_empresa).text.toString()
            val senha: String = findViewById<EditText>(R.id.edit_senha).text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                signInWithEmailAndPassword(email, senha)
            } else {
                Toast.makeText(this@MainActivity, "Por favor, preencha os campos", Toast.LENGTH_SHORT).show()
            }
        }

        //Logica para chamar outra tela
        val txt_Cadastro = findViewById<TextView>(R.id.txt_cadastro)
        txt_Cadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithEmailAndPassword(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmailAndPassword: Success")
                // Após o login bem-sucedido, buscar o tipo de usuário
                getUserDataAndRedirect(email)
            } else {
                Log.d(TAG, "signInWithEmailAndPassword: Failure", task.exception)
                Toast.makeText(baseContext, "Falha na autenticação", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getUserDataAndRedirect(email: String) {
        val db = FirebaseFirestore.getInstance()
        // Realiza uma consulta na tabela "usuarios" onde o campo "email" é igual ao email fornecido
        db.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        // Obtém o valor do campo "tipoUsuario"
                        val tipoUsuario = document.getString("tipoUsuario")

                        // Verifica o tipo de usuário e redireciona para a tela correspondente
                        when (tipoUsuario) {
                            "empresa" -> {
                                val intent = Intent(this, Feed_EmpresaActivity::class.java)
                                startActivity(intent)
                            }
                            "freelancer" -> {
                                val intent = Intent(this, Feed_FreeActivity::class.java)
                                startActivity(intent)
                            }
                            else -> {
                                Log.d(DB, "Tipo de usuário desconhecido: $tipoUsuario")
                                Toast.makeText(this, "Tipo de usuário desconhecido", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    // Caso não encontre nenhum documento com o email fornecido
                    Log.d(DB, "Nenhum usuário encontrado com esse email")
                    Toast.makeText(this, "Nenhum usuário encontrado com esse email", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                // Tratar o erro na consulta
                Log.w(DB, "Erro ao buscar usuário: ", exception)
                Toast.makeText(this, "Erro ao buscar usuário", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        var TAG  ="EmailAndSenha"
        var DB = "BancoDeDados"
    }

}