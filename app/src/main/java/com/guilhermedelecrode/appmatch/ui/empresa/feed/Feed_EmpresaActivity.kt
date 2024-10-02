package com.guilhermedelecrode.appmatch.ui.empresa.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.ui.empresa.ofertas.Ofertas_Enviadas_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico.Ordem_Servico_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.perfil.Perfil_EmpresaActivity
import com.guilhermedelecrode.appmatch.ui.empresa.vagas.Cadastrar_VagaActivity

class Feed_EmpresaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feed_empresa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_visualizar_ofertas = findViewById<Button>(R.id.btn_visuzalizar_ofertas)
        btn_visualizar_ofertas.setOnClickListener{
            val intent = Intent(this, Ofertas_Enviadas_EmpresaActivity::class.java)
            startActivity(intent)
        }
        onResume()

        window.statusBarColor = Color.parseColor("#00537D")

        window.navigationBarColor = ContextCompat.getColor(this, R.color.principal)

        val img_editar_vaga = findViewById<ImageView>(R.id.img_editar_vaga)

        img_editar_vaga.setOnClickListener {
            // Inflate o layout personalizado do dialog
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit_vaga, null)

            // Localize os campos de entrada no layout do dialog
            val editTextTitulo = dialogView.findViewById<EditText>(R.id.editTextTitulo)
            val editTextDescricao = dialogView.findViewById<EditText>(R.id.editTextDescricao)
            val editTextFerramenta = dialogView.findViewById<EditText>(R.id.editTextFerramenta)
            val editTextHabilidades = dialogView.findViewById<EditText>(R.id.editTextHabilidades)
            val editTextSalario = dialogView.findViewById<EditText>(R.id.editTextSalario)


            // Crie o AlertDialog
            AlertDialog.Builder(this)
                .setTitle("Editar Vaga")
                .setView(dialogView)
                .setPositiveButton("Salvar") { dialog, which ->
                    // Capture os valores editados pelo usuário
                    val novoTitulo = editTextTitulo.text.toString()
                    val novaDescricao = editTextDescricao.text.toString()
                    val novaFerramenta = editTextFerramenta.text.toString()
                    val novasHabilidades = editTextHabilidades.text.toString()
                    val novoSalario = editTextSalario.text.toString()

                    // Aqui você pode salvar ou enviar os dados editados conforme necessário
                    // Exemplo: Atualizar a vaga no banco de dados ou na interface
                    Toast.makeText(this, "Dados atualizados!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }


        // Habilitar suporte a ActionBar personalizada
        supportActionBar?.setDisplayShowHomeEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Definir o layout customizado na ActionBar
        val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
        supportActionBar?.customView = actionBarLayout
        supportActionBar?.setDisplayShowCustomEnabled(true)

        // Encontrar o botão no layout customizado da ActionBar
        val backButton = actionBarLayout.findViewById<Button>(R.id.action_bar_button)

        // Definir ação para o botão Voltar
        backButton.setOnClickListener {
            // Voltar à tela anterior ou realizar alguma ação
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_feed_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.feedPerfilEmpresa ->{
                Intent(this, Perfil_EmpresaActivity::class.java).apply{
                    startActivity(this)
                }
            }
            R.id.feedNovoServicoEmpresa -> {
                Intent(this, Cadastrar_VagaActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.feedOrdemServicoEmpresa ->{
                Intent(this, Ordem_Servico_EmpresaActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.feedSair -> {
                finishAffinity()
                }
            }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Log.d("MyActivity", "Atividade em execução: ${this::class.java.simpleName}")
    }
}
