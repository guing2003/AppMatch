package com.guilhermedelecrode.appmatch.adapter.freelancer

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
    private val ordensServico: List<OrdemServico>
) : RecyclerView.Adapter<OrdensServicoFreelancerAdapter.OrdemServicoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdemServicoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ordem_servico_free, parent, false)
        return OrdemServicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdemServicoViewHolder, position: Int) {
        holder.bind(ordensServico[position])
    }

    override fun getItemCount() = ordensServico.size

    inner class OrdemServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeProjeto: TextView = itemView.findViewById(R.id.txt_titulo_vaga)
        private val email: TextView = itemView.findViewById(R.id.txt_email)
        private val prazo: TextView = itemView.findViewById(R.id.txt_prazo)
        private val status: TextView = itemView.findViewById(R.id.txt_status)

        fun bind(ordemServico: OrdemServico) {
            nomeProjeto.text = "Titulo da vaga: ${ordemServico.nomeProjeto}"
            email.text = "Email: ${ordemServico.email}"
            prazo.text = "Prazo: ${ordemServico.prazo}"
            status.text = "Status: ${ordemServico.status}"
        }
    }
}