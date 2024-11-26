package com.guilhermedelecrode.appmatch.adapter.freelancer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.freelancer.vagas.Detalhes_Vaga_FreeActivity

class Feed_FreelancerAdapter( private val context: Context,
                              val vagaList: MutableList<Vaga>) : RecyclerView.Adapter<Feed_FreelancerAdapter.feedFreeViewHolder>() {


    inner class feedFreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txt_titulo_vaga_free : TextView = itemView.findViewById(R.id.txt_titulo_free)
        val txt_habilidade_free : TextView = itemView.findViewById(R.id.txt_habilidade_free)
        val txt_salario_free : TextView = itemView.findViewById(R.id.txt_salario_free)
        val btn_detalhes_free : Button = itemView.findViewById(R.id.btn_detalhes_vaga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedFreeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vaga_free, parent, false)
        return feedFreeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: feedFreeViewHolder, position: Int){
        val vaga = vagaList[position]
        holder.txt_titulo_vaga_free.text = "Nome do projeto: ${vaga.nomeProjeto}"
        //holder.txt_descricao.text = "Descrição: ${vaga.descricao}"
        holder.txt_habilidade_free.text = "Habilidades: ${vaga.habilidades}"
       // holder.txt_email.text = "Email: ${vaga.email}"
        holder.txt_salario_free.text = "Valor Proposto: R$${vaga.valorPago}"

        // Configurar botão de detalhes
        holder.btn_detalhes_free.setOnClickListener {
            val intent = Intent(context, Detalhes_Vaga_FreeActivity::class.java)
            intent.putExtra("idVaga", vaga.idVaga) // 'vaga.id' contém o ID da vaga selecionada
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
