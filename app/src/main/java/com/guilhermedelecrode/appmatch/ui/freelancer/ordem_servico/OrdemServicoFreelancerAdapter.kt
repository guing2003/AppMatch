package com.guilhermedelecrode.appmatch.ui.freelancer.ordem_servico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico
class OrdensServicoFreelancerAdapter(
    private val context: Context,
    private var ordensServico: MutableList<OrdemServico> // Tornamos a lista mutável
) : RecyclerView.Adapter<OrdensServicoFreelancerAdapter.OrdemServicoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdemServicoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ordem_servico_freelancer, parent, false)
        return OrdemServicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdemServicoViewHolder, position: Int) {
        holder.bind(ordensServico[position])
    }

    override fun getItemCount() = ordensServico.size

    // Método para atualizar a lista de ordens
    fun updateOrdensServico(novasOrdens: List<OrdemServico>) {
        ordensServico.clear()
        ordensServico.addAll(novasOrdens)
        notifyDataSetChanged() // Notifica o adapter sobre a atualização dos dados
    }

    inner class OrdemServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeProjeto: TextView = itemView.findViewById(R.id.txt_titulo_vaga_item_ordem_servico_freelancer)
        private val email: TextView = itemView.findViewById(R.id.txt_email_item_ordem_servico_freelancer)
        private val prazo: TextView = itemView.findViewById(R.id.txt_prazo_item_ordem_servico_freelancer)
        private val status: TextView = itemView.findViewById(R.id.txt_status_item_ordem_servico_freelancer)

        fun bind(ordemServico: OrdemServico) {
            nomeProjeto.text = "Titulo da vaga: ${ordemServico.nomeProjeto}"
            email.text = "Email: ${ordemServico.email}"
            prazo.text = "Prazo: ${ordemServico.prazo}"
            status.text = "Status: ${ordemServico.status}"
        }
    }
}

