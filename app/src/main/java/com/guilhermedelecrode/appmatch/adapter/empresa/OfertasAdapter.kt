package com.guilhermedelecrode.appmatch.adapter.empresa

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Ofertas
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.model.freelancer.OrdemServico


class OfertasAdapter(
    private val context: Context,
    private val offers: List<Ofertas>,
    private val vaga: Vaga,
    private val onStatusChange: (Ofertas, String) -> Unit
) : RecyclerView.Adapter<OfertasAdapter.OfertaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        holder.bind(offers[position], vaga)
    }

    override fun getItemCount() = offers.size

    inner class OfertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeUser = itemView.findViewById<TextView>(R.id.txt_nome_free)
        private val nomeVaga = itemView.findViewById<TextView>(R.id.txt_titulo_vaga)
        private val habilidades = itemView.findViewById<TextView>(R.id.txt_habilidades)
        private val preco = itemView.findViewById<TextView>(R.id.txt_salario_proposto)
        private val prazo = itemView.findViewById<TextView>(R.id.txt_prazo)
        private val aceitar = itemView.findViewById<ImageView>(R.id.img_aceitar_oferta)
        private val recusar = itemView.findViewById<ImageView>(R.id.img_recusar_oferta)

        fun bind(oferta: Ofertas, vaga: Vaga) {
            nomeUser.text = "Nome do Freelancer: ${oferta.nome}"
            nomeVaga.text = "Nome do projeto: ${vaga.nomeProjeto}"
            prazo.text = "Prazo Proposto: ${oferta.prazo}"
            preco.text = "Valor Proposto: ${oferta.preco}"
            habilidades.text = "Habilidades: ${oferta.status}"

            aceitar.setOnClickListener {
                Log.d("OfertasAdapter", "Aceitar clicado para oferta: ${oferta.idOferta}")
                createOrderOfService(oferta, vaga)
                onStatusChange(oferta, "Andamento")
            }
            //Revisar isto aqui depois
            recusar.setOnClickListener {
                Log.d("OfertasAdapter", "Recusar clicado para oferta: ${oferta.idOferta}")
                onStatusChange(oferta, "Recusada")
            }
        }

        private fun createOrderOfService(oferta: Ofertas, vaga: Vaga) {
            val db = FirebaseFirestore.getInstance()
            val ordemServico = OrdemServico(
                idOrdemServico = db.collection("ordemServicos").document().id,
                idVaga = vaga.idVaga,
                idUserEmpresa = vaga.idUser,
                idUserFree = oferta.idUserFree,
                nomeProjeto = vaga.nomeProjeto,
                nomeFreelancer = oferta.nome,
                prazo = oferta.prazo,
                email = vaga.email,
                status = "Em andamento"
            )

            db.collection("ordemServicos").document(ordemServico.idOrdemServico)
                .set(ordemServico)
                .addOnSuccessListener {
                    Log.d("OfertasAdapter", "Ordem de serviço criada com sucesso: ${ordemServico.idOrdemServico}")
                    deleteOffer(oferta)
                }
                .addOnFailureListener { e ->
                    Log.e("OfertasAdapter", "Erro ao criar ordem de serviço: ${e.message}")
                }
        }

        private fun deleteOffer(oferta: Ofertas) {
            val db = FirebaseFirestore.getInstance()
            db.collection("ofertas").document(oferta.idOferta)
                .delete()
                .addOnSuccessListener {
                    Log.d("OfertasAdapter", "Oferta excluída com sucesso: ${oferta.idOferta}")
                }
                .addOnFailureListener { e ->
                    Log.e("OfertasAdapter", "Erro ao excluir oferta: ${e.message}")
                }
        }
    }
}
