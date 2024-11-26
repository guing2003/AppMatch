package com.guilhermedelecrode.appmatch.adapter.freelancer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.freelancer.vagas.Detalhes_Vaga_FreeActivity

class DetalhesVagaAdapter(
    private val context: Context,
    private val detalhesList:  MutableList<Vaga> // Lista de informações da vaga (ex.: requisitos)
) : RecyclerView.Adapter<DetalhesVagaAdapter.DetalhesViewHolder>() {

    // ViewHolder para gerenciar o layout de cada item
    class DetalhesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_titulo_vaga: TextView = itemView.findViewById(R.id.txt_titulo_vaga)
        val txt_nome_empresa : TextView = itemView.findViewById(R.id.txt_nome_empresa)
        val txt_descricao : TextView = itemView.findViewById(R.id.txt_descricao)
        val txt_habilidades : TextView = itemView.findViewById(R.id.txt_habilidades)
        val  txt_email : TextView = itemView.findViewById(R.id.txt_email)
        val txt_salario : TextView = itemView.findViewById(R.id.txt_salario)
        val btn_fazer_oferta : Button = itemView.findViewById(R.id.btn_fazer_oferta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalhesViewHolder {
        // Infla o layout do item
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_descricao_vaga, parent, false)
        return DetalhesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetalhesViewHolder, position: Int) {
        // Vincula os dados do item à ViewHolder
        val vaga = detalhesList[position]
        holder.txt_titulo_vaga.text = "Nome do projeto: ${vaga.nomeProjeto}"
        holder.txt_nome_empresa.text = "Nome da Empresa: ${vaga.nomeEmpresa}"
        holder.txt_descricao.text = "Descrição:  ${vaga.descricao}"
        holder.txt_habilidades.text = "Habilidades: ${vaga.habilidades}"
        holder.txt_email.text = "Email: ${vaga.email}"
        holder.txt_salario.text = "Valor Proposto: R$${vaga.valorPago}"

        // Configurar botão de detalhes
        // Configurar o botão
        holder.btn_fazer_oferta.setOnClickListener {

            val idVaga = vaga.idVaga
            val idUser = vaga.idUser

            // Obter o ID do usuário logado
            val currentUser = FirebaseAuth.getInstance().currentUser
            val id = currentUser?.uid.toString()

            showOfferDialog(idVaga, idUser, id)
        }
    }
    private fun showOfferDialog(idVaga: String, idUser: String, id: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_oferta, null)
        val editTextPreco = dialogView.findViewById<EditText>(R.id.editTextPreco)
        val editTextPrazo = dialogView.findViewById<EditText>(R.id.editTextPrazo)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("Enviar") { _, _ ->
                val preco = editTextPreco.text.toString()
                val prazo = editTextPrazo.text.toString()

                // Cria um objeto com os dados da oferta
                val oferta = hashMapOf(
                    "idVaga" to idVaga,
                    "idUser" to idUser,
                    "id" to id,
                    "preco" to preco,
                    "prazo" to prazo,
                    "status" to "em analise"
                )

                // Enviar os dados ao banco de dados
                val db = FirebaseFirestore.getInstance()
                db.collection("ofertas").add(oferta)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Oferta enviada com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Erro ao enviar oferta: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }


    override fun getItemCount(): Int {
        // Retorna o tamanho da lista
        return detalhesList.size
    }
}
