package com.guilhermedelecrode.appmatch.ui.cadastro

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import com.guilhermedelecrode.appmatch.common.AbstractActivity
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.cadastro.fragment.CadastroEmpresaFragment
import com.guilhermedelecrode.appmatch.ui.cadastro.fragment.CadastroFreelancerFragment

class CadastroActivity : AbstractActivity() {

    private lateinit var btn_freelance : Button
    private lateinit var btn_empresa: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()



        //Fragment
        btn_freelance = findViewById(R.id.btn_freelance)
        btn_empresa = findViewById(R.id.btn_empresa)


        /*
        val fragmentManager = supportFragmentManager.beginTransaction()
        //alteraçõoes em fragments
        fragmentManager.add( R.id.fragment_conteudo, ConversasFragment() )
        fragmentManager.commit()
        */

        val freeFragment = CadastroFreelancerFragment()
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