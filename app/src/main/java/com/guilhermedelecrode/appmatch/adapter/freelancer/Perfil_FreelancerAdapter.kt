package com.guilhermedelecrode.appmatch.adapter.freelancer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.freelancer.PerfilFree
import com.guilhermedelecrode.appmatch.ui.freelancer.perfil.Edit_Perfil_FreeActivity

class Perfil_FreelancerAdapter(private val perfilList: MutableList<PerfilFree>,
                               private val context: Context
) : RecyclerView.Adapter<Perfil_FreelancerAdapter.perfilViewHolder>()  {

    inner class perfilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_nome_free: TextView = itemView.findViewById(R.id.txt_nome_free)
        val txt_cpf: TextView = itemView.findViewById(R.id.txt_cpf)
        val txt_endereco_free: TextView = itemView.findViewById(R.id.txt_endereco_free)
        val txt_email_free: TextView = itemView.findViewById(R.id.txt_email_free)
        val txt_telefone_free : TextView = itemView.findViewById(R.id.txt_telefone_free)
        val txt_descricao_free: TextView = itemView.findViewById(R.id.txt_descricao_free)
        val btn_edit: Button = itemView.findViewById(R.id.btn_editar_perfil_free)

        init {
            // Adiciona o Listener no botão de cada item do RecyclerView
            btn_edit.setOnClickListener {
                // Ação ao clicar no botão
                val intent = Intent(context, Edit_Perfil_FreeActivity::class.java)
                context.startActivity(intent) // Usa o contexto passado para iniciar a Activity
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Perfil_FreelancerAdapter.perfilViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_perfil_free, parent, false)
        return perfilViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: Perfil_FreelancerAdapter.perfilViewHolder,
        position: Int
    ) {
        val perfil = perfilList[position]
        holder.txt_nome_free.text = "Nome: ${perfil.nome_free}"
        holder.txt_cpf.text = "CPF: ${perfil.cpf}"
        holder.txt_endereco_free.text = "Endereço: ${perfil.endereco} ${perfil.numero}"
        holder.txt_email_free.text = "Email: ${perfil.email}"
        holder.txt_telefone_free.text = "Telefone: ${perfil.telefone}"
        holder.txt_descricao_free.text = "Descrição: ${perfil.descricao}"
    }

    override fun getItemCount(): Int = perfilList.size

    fun updateData(newPerfilList: List<PerfilFree>) {
        perfilList.clear()
        perfilList.addAll(newPerfilList)
        notifyDataSetChanged()
    }
}