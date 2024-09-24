package com.guilhermedelecrode.appmatch


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.ui.cadastro.CadastroActivity
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var edit_email : EditText
    private lateinit var edit_senha : EditText
    private lateinit var btn_login : Button
    private lateinit var txt_cadastro : Text

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onResume()

        //Logica para chamar outra tela
        val txt_Cadastro = findViewById<TextView>(R.id.txt_cadastro)
        txt_Cadastro.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        Log.d("MyActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}