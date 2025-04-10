package com.guilhermedelecrode.appmatch.ui.freelancer.feed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.freelancer.vagas.DetalhesVagaFreelancerActivity

class FeedFreelancerAdapter(private val context: Context,
                            val vagaList: MutableList<Vaga>) : RecyclerView.Adapter<FeedFreelancerAdapter.feedFreeViewHolder>() {


    inner class feedFreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txt_titulo_vaga_free : TextView = itemView.findViewById(R.id.txt_titulo_vaga_feed_freelancer_activity)
        val txt_habilidade_free : TextView = itemView.findViewById(R.id.txt_habilidade_vaga_feed_freelancer_activity)
        val txt_salario_free : TextView = itemView.findViewById(R.id.txt_salario_vaga_feed_freelancer_activity)
        val btn_detalhes_free : Button = itemView.findViewById(R.id.btn_detalhes_vaga_feed_freelancer_activity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedFreeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vaga_freelancer, parent, false)
        return feedFreeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: feedFreeViewHolder, position: Int){
        val vaga = vagaList[position]
        holder.txt_titulo_vaga_free.text = "Nome do projeto: ${vaga.nomeProjeto}"
        //holder.txt_descricao.text = "Descrição: ${vaga.descricao}"
        holder.txt_habilidade_free.text = "Habilidades: ${vaga.habilidades}"
       // holder.txt_email.text = "Email: ${vaga.email}"
        holder.txt_salario_free.text = "Valor Proposto: R$${vaga.valorPago}"

        holder.btn_detalhes_free.setOnClickListener {
            val intent = Intent(context, DetalhesVagaFreelancerActivity::class.java)
            intent.putExtra("idVaga", vaga.idVaga)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = vagaList.size

    fun updateData(newVagaList: List<Vaga>) {
        vagaList.clear()
        vagaList.addAll(newVagaList)
        notifyDataSetChanged()
    }
}
