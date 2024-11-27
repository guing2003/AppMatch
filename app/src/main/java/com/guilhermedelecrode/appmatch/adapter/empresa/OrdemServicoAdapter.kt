package com.guilhermedelecrode.appmatch.adapter.empresa

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico

class OrdensServicoAdapter(
    private val context: Context,
    private val ordensServico: List<OrdemServico>
) : RecyclerView.Adapter<OrdensServicoAdapter.OrdemServicoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdemServicoViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_ordem_servico_empresa, parent, false)
        return OrdemServicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdemServicoViewHolder, position: Int) {
        holder.bind(ordensServico[position])
    }

    override fun getItemCount() = ordensServico.size

    inner class OrdemServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeProjeto: TextView = itemView.findViewById(R.id.txt_titulo_vaga)
        private val nomeFreelancer: TextView = itemView.findViewById(R.id.txt_nome_free)
        private val email: TextView = itemView.findViewById(R.id.txt_email)
        private val prazo: TextView = itemView.findViewById(R.id.txt_prazo)
        private val status: TextView = itemView.findViewById(R.id.txt_status)
        private val edit_ordem: ImageView = itemView.findViewById(R.id.img_edit_ordem_servico)

        init {
            edit_ordem.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    showStatusDialog(ordensServico[position])
                }
            }
        }

        fun bind(ordemServico: OrdemServico) {
            nomeProjeto.text = "Titulo da vaga: ${ordemServico.nomeProjeto}"
            nomeFreelancer.text = "Nome do Freelancer: ${ordemServico.nomeFreelancer}"
            email.text = "Email: ${ordemServico.email}"
            prazo.text = "Prazo: ${ordemServico.prazo}"
            status.text = "Status: ${ordemServico.status}"
        }

        private fun showStatusDialog(ordemServico: OrdemServico) {
            val statusOptions = arrayOf("Em Andamento", "Concluída")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Alterar Status").setItems(
                statusOptions
            ) { _, which ->
                val newStatus = statusOptions[which]
                updateOrdemServicoStatus(ordemServico, newStatus)
            }
            builder.create().show()
        }

        private fun updateOrdemServicoStatus(ordemServico: OrdemServico, newStatus: String) {
            val db = FirebaseFirestore.getInstance()
            val ordemServicoRef = db.collection("ordemServicos")
                .document(ordemServico.idOrdemServico)
            ordemServicoRef.update("status", newStatus).addOnSuccessListener {
                Toast.makeText(context, "Status atualizado para $newStatus", Toast.LENGTH_SHORT)
                    .show()
                Log.d(
                    "OrdensServicoAdapter",
                    "Status atualizado com sucesso para $newStatus"
                ) // Atualize a lista e notifique o adapter
                ordemServico.status = newStatus
                notifyDataSetChanged()
            }.addOnFailureListener { e ->
                Toast.makeText(
                    context,
                    "Erro ao atualizar status: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("OrdensServicoAdapter", "Erro ao atualizar status: ${e.message}")
            }
        }
    }
}
