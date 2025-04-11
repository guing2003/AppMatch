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

    fun updateOrdensServico(novasOrdens: List<OrdemServico>) {
        ordensServico.clear()
        ordensServico.addAll(novasOrdens)
        notifyDataSetChanged()
    }

    inner class OrdemServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeProjeto: TextView = itemView.findViewById(R.id.txt_titulo_vaga_ordem_servico_freelancer_activity)
        private val email: TextView = itemView.findViewById(R.id.txt_email_ordem_servico_freelancer_activity)
        private val prazo: TextView = itemView.findViewById(R.id.txt_prazo_ordem_servico_freelancer_activity)
        private val status: TextView = itemView.findViewById(R.id.txt_status_ordem_servico_freelancer_activity)

        fun bind(ordemServico: OrdemServico) {
            nomeProjeto.text = "Titulo da vaga: ${ordemServico.nomeProjeto}"
            email.text = "Email: ${ordemServico.email}"
            prazo.text = "Prazo: ${ordemServico.prazo}"
            status.text = "Status: ${ordemServico.status}"
        }
    }
}

