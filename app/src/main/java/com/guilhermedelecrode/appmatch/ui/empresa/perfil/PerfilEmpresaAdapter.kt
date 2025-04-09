package com.guilhermedelecrode.appmatch.ui.empresa.perfil

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.PerfilEmpresa


class PerfilEmpresaAdapter(private val perfilList: MutableList<PerfilEmpresa>,
                           private val context: Context
) : RecyclerView.Adapter<PerfilEmpresaAdapter.perfilViewHolder>() {

    inner class perfilViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_nome_empresa: TextView = itemView.findViewById(R.id.txt_nome_empresa)
        val txt_cnpj: TextView = itemView.findViewById(R.id.txt_cnpj)
        val txt_endereco_empresa: TextView = itemView.findViewById(R.id.txt_endereco_empresa)
        val txt_email_empresa: TextView = itemView.findViewById(R.id.txt_email_empresa)
        val txt_telefone_empresa : TextView = itemView.findViewById(R.id.txt_telefone_empresa)
        val txt_seguimento: TextView = itemView.findViewById(R.id.txt_seguimento)
        val btn_edit: Button = itemView.findViewById(R.id.btn_editar_perfil_empresa)

        init {
            // Adiciona o Listener no botão de cada item do RecyclerView
            btn_edit.setOnClickListener {
                // Ação ao clicar no botão
                val intent = Intent(context, EditarPerfilEmpresaActivity::class.java)
                context.startActivity(intent) // Usa o contexto passado para iniciar a Activity
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): perfilViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_perfil_empresa, parent, false)
        return perfilViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: perfilViewHolder, position: Int) {
        val perfil = perfilList[position]
        holder.txt_nome_empresa.text = "Nome: ${perfil.nome_empresa}"
        holder.txt_cnpj.text = "CNPJ: ${perfil.cnpj}"
        holder.txt_endereco_empresa.text = "Endereço: ${perfil.endereco} ${perfil.numero}"
        holder.txt_email_empresa.text = "Email: ${perfil.email}"
        holder.txt_telefone_empresa.text = "Telefone: ${perfil.telefone}"
        holder.txt_seguimento.text = "Seguimento: ${perfil.seguimento}"
    }

    override fun getItemCount(): Int = perfilList.size

    fun updateData(newPerfilList: List<PerfilEmpresa>) {
        perfilList.clear()
        perfilList.addAll(newPerfilList)
        notifyDataSetChanged()
    }
}
