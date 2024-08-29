package com.guilhermedelecrode.appmatch

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.fragment.CadastroEmpresaFragment
import com.guilhermedelecrode.appmatch.fragment.CadastroFreeFragment

class CadastroActivity : AppCompatActivity() {

    private lateinit var btn_freelance : Button
    private lateinit var btn_empresa: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btn_freelance = findViewById(R.id.btn_freelance)
        btn_empresa = findViewById(R.id.btn_empresa)


        /*
        val fragmentManager = supportFragmentManager.beginTransaction()
        //alteraçõoes em fragments
        fragmentManager.add( R.id.fragment_conteudo, ConversasFragment() )
        fragmentManager.commit()
        */

        val freeFragment = CadastroFreeFragment()
        val empresaFragment = CadastroEmpresaFragment()

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
}