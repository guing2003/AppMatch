package com.guilhermedelecrode.appmatch.ui.empresa.ordem_servico

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico

class OrdensServicoAdapter(
    private val context: Context,
    private val ordensServicoOriginal: List<OrdemServico>
) : RecyclerView.Adapter<OrdensServicoAdapter.OrdemServicoViewHolder>() {

    private val ordensServicoExibidas = mutableListOf<OrdemServico>()

    init {
        ordensServicoExibidas.addAll(ordensServicoOriginal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdemServicoViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_ordem_servico_empresa, parent, false)
        return OrdemServicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdemServicoViewHolder, position: Int) {
        holder.bind(ordensServicoExibidas[position])
    }

    override fun getItemCount() = ordensServicoExibidas.size

    fun updateData(newData: List<OrdemServico>) {
        ordensServicoExibidas.clear()
        ordensServicoExibidas.addAll(newData)
        notifyDataSetChanged()
    }

    inner class OrdemServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeProjeto: TextView = itemView.findViewById(R.id.txt_titulo_vaga)
        private val nomeFreelancer: TextView = itemView.findViewById(R.id.txt_nome_free)
        private val email: TextView = itemView.findViewById(R.id.txt_email)
        private val prazo: TextView = itemView.findViewById(R.id.txt_prazo)
        private val status: TextView = itemView.findViewById(R.id.txt_status)
        private val editOrdem: ImageView = itemView.findViewById(R.id.img_edit_ordem_servico)
        private val avaliar: ImageView = itemView.findViewById(R.id.img_avaliar)

        init {
            editOrdem.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val ordem = ordensServicoExibidas[position]
                    showStatusDialog(ordem, position)
                }
            }

            avaliar.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val ordem = ordensServicoExibidas[position]
                    showAvaliacaoDialog(ordem)
                }
            }
        }

        fun bind(ordemServico: OrdemServico) {
            nomeProjeto.text = "Título da vaga: ${ordemServico.nomeProjeto}"
            nomeFreelancer.text = "Nome do Freelancer: ${ordemServico.nomeFreelancer}"
            email.text = "Email: ${ordemServico.email}"
            prazo.text = "Prazo: ${ordemServico.prazo}"
            status.text = "Status: ${ordemServico.status}"
        }

        private fun showStatusDialog(ordemServico: OrdemServico, position: Int) {
            val statusOptions = arrayOf("Em andamento", "Concluída")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Alterar Status")
                .setItems(statusOptions) { _, which ->
                    val newStatus = statusOptions[which]
                    updateOrdemServicoStatus(ordemServico, newStatus, position)
                }
            builder.create().show()
        }

        private fun updateOrdemServicoStatus(
            ordemServico: OrdemServico,
            newStatus: String,
            position: Int
        ) {
            val db = FirebaseFirestore.getInstance()
            val ordemServicoRef = db.collection("ordemServicos")
                .document(ordemServico.idOrdemServico)

            ordemServicoRef.update("status", newStatus).addOnSuccessListener {
                Toast.makeText(context, "Status atualizado para $newStatus", Toast.LENGTH_SHORT)
                    .show()
                Log.d("OrdensServicoAdapter", "Status atualizado com sucesso para $newStatus")

                ordensServicoExibidas[position].status = newStatus
                notifyItemChanged(position)
            }.addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao atualizar status: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e("OrdensServicoAdapter", "Erro ao atualizar status: ${e.message}")
            }
        }

        private fun showAvaliacaoDialog(ordemServico: OrdemServico) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_avaliar, null)
            val ratingBar: RatingBar = dialogView.findViewById(R.id.avaliacaoFreelancer)
            val editAvaliacao: EditText = dialogView.findViewById(R.id.edit_avaliar)

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Avaliar Freelancer")
                .setView(dialogView)
                .setPositiveButton("Salvar") { _, _ ->
                    val rating = ratingBar.rating
                    val avaliacaoTexto = editAvaliacao.text.toString()
                    salvarAvaliacao(ordemServico.idUserFree, rating, avaliacaoTexto)
                }
                .setNegativeButton("Cancelar", null)
            builder.create().show()
        }

        private fun salvarAvaliacao(idUserFree: String, rating: Float, avaliacaoTexto: String) {
            val db = FirebaseFirestore.getInstance()
            val avaliacao = hashMapOf(
                "idFreelancer" to idUserFree,
                "rating" to rating,
                "avaliacaoTexto" to avaliacaoTexto
            )

            db.collection("avaliacoes").add(avaliacao).addOnSuccessListener {
                Toast.makeText(context, "Avaliação salva com sucesso!", Toast.LENGTH_SHORT).show()
                Log.d("OrdensServicoAdapter", "Avaliação salva com sucesso!")
            }.addOnFailureListener { e ->
                Toast.makeText(context, "Erro ao salvar avaliação: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e("OrdensServicoAdapter", "Erro ao salvar avaliação: ${e.message}")
            }
        }
    }
}
