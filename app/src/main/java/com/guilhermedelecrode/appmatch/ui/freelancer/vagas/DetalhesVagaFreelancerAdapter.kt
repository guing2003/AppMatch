package com.guilhermedelecrode.appmatch.ui.freelancer.vagas

import android.content.Context
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

class DetalhesVagaFreelancerAdapter(
    private val context: Context,
    private val detalhesList: MutableList<Vaga>
) : RecyclerView.Adapter<DetalhesVagaFreelancerAdapter.DetalhesViewHolder>() {

    class DetalhesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_titulo_vaga: TextView = itemView.findViewById(R.id.txt_titulo_vaga_item_descricao_vaga_freelancer)
        val txt_nome_empresa: TextView = itemView.findViewById(R.id.txt_nome_empresa_item_descricao_vaga_freelancer)
        val txt_descricao: TextView = itemView.findViewById(R.id.txt_descricao_vaga_item_descricao_vaga_freelancer)
        val txt_habilidades: TextView = itemView.findViewById(R.id.txt_habilidades_vaga_item_descricao_vaga_freelancer)
        val txt_email: TextView = itemView.findViewById(R.id.txt_email_vaga_item_descricao_vaga_freelancer)
        val txt_salario: TextView = itemView.findViewById(R.id.txt_salario_vaga_item_descricao_vaga_freelancer)
        val btn_fazer_oferta: Button = itemView.findViewById(R.id.btn_fazer_oferta_vaga_item_descricao_vaga_freelancer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalhesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_descricao_vaga, parent, false)
        return DetalhesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DetalhesViewHolder, position: Int) {
        val vaga = detalhesList[position]
        holder.txt_titulo_vaga.text = "Nome do projeto: ${vaga.nomeProjeto}"
        holder.txt_nome_empresa.text = "Nome da Empresa: ${vaga.nomeEmpresa}"
        holder.txt_descricao.text = "Descrição: ${vaga.descricao}"
        holder.txt_habilidades.text = "Habilidades: ${vaga.habilidades}"
        holder.txt_email.text = "Email: ${vaga.email}"
        holder.txt_salario.text = "Valor Proposto: R$${vaga.valorPago}"

        holder.btn_fazer_oferta.setOnClickListener {
            val idVaga = vaga.idVaga
            val idUserEmpresa = vaga.idUser

            val db = FirebaseFirestore.getInstance()
            val currentUser = FirebaseAuth.getInstance().currentUser
            val idUserFree = currentUser?.uid.toString()

            val documentoRef = db.collection("usuarios").document(idUserFree)

            documentoRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val nomeUser = documentSnapshot.getString("nome")
                    if (nomeUser != null) {
                        println("Nome encontrado: $nomeUser")
                        showOfferDialog(idVaga, idUserEmpresa, idUserFree, nomeUser)
                    } else {
                        println("Campo 'nome' não encontrado no documento")
                    }
                } else {
                    println("Documento não encontrado")
                }
            }.addOnFailureListener { exception ->
                println("Erro ao buscar documento: ${exception.message}")
            }
        }
    }

    private fun showOfferDialog(
        idVaga: String,
        idUserEmpresa: String,
        idUserFree: String,
        nomeUser: String
    ) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_oferta, null)
        val editTextPreco = dialogView.findViewById<EditText>(R.id.editTextPreco)
        val editTextPrazo = dialogView.findViewById<EditText>(R.id.editTextPrazo)

        AlertDialog.Builder(context)
            .setView(dialogView)
            .setPositiveButton("Enviar") { _, _ ->
                val preco = editTextPreco.text.toString()
                val prazo = editTextPrazo.text.toString()

                val db = FirebaseFirestore.getInstance()
                val idOferta = db.collection("ofertas").document().id

                val oferta = hashMapOf(
                    "idOferta" to idOferta,
                    "idVaga" to idVaga,
                    "idUserEmpresa" to idUserEmpresa,
                    "idUserFree" to idUserFree,
                    "nome" to nomeUser,
                    "preco" to preco,
                    "prazo" to prazo,
                    "status" to "em analise"
                )

                db.collection("ofertas").document(idOferta).set(oferta)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Oferta enviada com sucesso!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            context,
                            "Erro ao enviar oferta: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    override fun getItemCount(): Int {
        return detalhesList.size
    }
}
