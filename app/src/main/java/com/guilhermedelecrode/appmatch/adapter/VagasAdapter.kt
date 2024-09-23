package com.guilhermedelecrode.appmatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.data.Vagas

class VagasAdapter : RecyclerView.Adapter<VagasAdapter.VagasViewHolder>() {

    inner class VagasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<Vagas>() {
        //Verifica se dois itens representam o mesmo objeto ou não
        override fun areItemsTheSame(oldItem: Vagas, newItem: Vagas): Boolean {

            return oldItem == newItem
        }

        //Verifica se os dois itens possuim o mesmo dado ou conteudo
        override fun areContentsTheSame(oldItem: Vagas, newItem: Vagas): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VagasViewHolder =
        VagasViewHolder(
            LayoutInflater.from(parent.context)
                . inflate(
                    R.layout.items_vagas_cadastradas,
                    parent,
                    false
                ) //Item view inflado para exibir no recycleer view
        )


    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: VagasAdapter.VagasViewHolder, position : Int) {
        val vagas = differ.currentList[position]
        holder.itemView.apply {
            val textViewTitle = findViewById<TextView>(R.id.txt_title)
            val textViewDescription = findViewById<TextView>(R.id.txt_description)
            val textViewAbility = findViewById<TextView>(R.id.txt_ability)
            val textViewPrice = findViewById<TextView>(R.id.txt_price)
            val textViewEmail = findViewById<TextView>(R.id.txt_email)

            textViewTitle.text = vagas.title
            textViewDescription.text = vagas.description
            textViewAbility.text = vagas.ability
            textViewPrice.text = vagas.price
            textViewEmail.text = vagas.email

            setOnClickListener{
                onItemClickListener?.let{ click ->
                    click(vagas)
                }
            }
        }
    }

    //Clicar na vaga
    private var onItemClickListener : ((Vagas)-> Unit)? = null

    fun setOnClickListener(listener : (Vagas)-> Unit){
        onItemClickListener = listener
    }
}