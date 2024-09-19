package com.guilhermedelecrode.appmatch

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.fragment.CadastroEmpresaFragment
import com.guilhermedelecrode.appmatch.fragment.CadastroFreeFragment

class CadastroActivity : AppCompatActivity() {

    private lateinit var btn_freelance : Button
    private lateinit var btn_empresa: Button

    private lateinit var auth: FirebaseAuth
    //private var binding : Bin = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Authentication Firebase
        auth = Firebase.auth

        //Fragment
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

    private fun createUserWithEmailAndPassword(email: String, password:String){
        //Chamando a função com os parametros para criar um novo user
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Log.d(TAG, "createUserWithEmailAndPassword:Success") //Mostrar no log se criou com sucesso o usuario
                val user = auth.currentUser
            }else{
                Log.w(TAG,"createUserWithEmailAndPassword:Failure", task.exception) //Mostrar no log se falhou a criação do novo usuario
                Toast.makeText(baseContext, "Authentication Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object{
        private var TAG = "EmailAndPassword"
    }
}