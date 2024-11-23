package com.guilhermedelecrode.appmatch.ui.freelancer.perfil

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.AbstractActivity
import com.guilhermedelecrode.appmatch.R

class Edit_Perfil_FreeActivity : AbstractActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil_free)

        // Configurar a ActionBar geral
        configActionBarGeral()
        onResume()

        val btn_cancelar_edit_free = findViewById<Button>(R.id.btn_cancelar_edit_empresa)
        val btn_salvar_edit_free = findViewById<Button>(R.id.btn_salvar_edit_empresa)
        btn_salvar_edit_free.setOnClickListener {
            val intent = Intent(this, Perfil_FreeActivity::class.java)
            startActivity(intent)
        }
        btn_cancelar_edit_free.setOnClickListener {
            val intent = Intent(this, Perfil_FreeActivity::class.java)
            startActivity(intent)
        }
    }
}