package com.guilhermedelecrode.appmatch.ui.cadastro

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.cadastro.fragment.Cadastro_EmpresaFragment
import com.guilhermedelecrode.appmatch.ui.cadastro.fragment.Cadastro_FreeFragment

class CadastroActivity : AppCompatActivity() {

    private lateinit var btn_freelance : Button
    private lateinit var btn_empresa: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onResume()

        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#00537D")

        //Fragment
        btn_freelance = findViewById(R.id.btn_freelance)
        btn_empresa = findViewById(R.id.btn_empresa)


        /*
        val fragmentManager = supportFragmentManager.beginTransaction()
        //alteraçõoes em fragments
        fragmentManager.add( R.id.fragment_conteudo, ConversasFragment() )
        fragmentManager.commit()
        */

        val freeFragment = Cadastro_FreeFragment()
        val empresaFragment = Cadastro_EmpresaFragment()

        btn_freelance.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentCadastro, freeFragment )
                .commit() }

        btn_empresa.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentCadastro, empresaFragment) //Para chamar um fragment
                //.remove(empresaFragment)//Para remover um fragment
                .commit() }
    }
    override fun onResume() {
        super.onResume()
        Log.d("MyActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}