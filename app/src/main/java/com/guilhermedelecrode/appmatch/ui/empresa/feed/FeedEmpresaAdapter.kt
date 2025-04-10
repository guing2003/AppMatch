package com.guilhermedelecrode.appmatch.ui.empresa.feed

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.guilhermedelecrode.appmatch.R
import com.guilhermedelecrode.appmatch.model.empresa.Vaga
import com.guilhermedelecrode.appmatch.ui.empresa.ofertas.OfertasEmpresaActivity

class FeedEmpresaAdapter(
    private val context: Context,
    private val vagaList: MutableList<Vaga>
) : RecyclerView.Adapter<FeedEmpresaAdapter.feedViewHolder>() {


    inner class feedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_nome_vaga_empresa: TextView = itemView.findViewById(R.id.txt_nome_vaga_empresa)
        val txt_descricao_vaga_empresa: TextView = itemView.findViewById(R.id.txt_descricao_vaga_empresa)
        val txt_habilidade_vaga_empresa: TextView = itemView.findViewById(R.id.txt_habilidades_vaga_empresa)
        val txt_email_vaga_empresa: TextView = itemView.findViewById(R.id.txt_email_vaga_empresa)
        val txt_valor_Pago_vaga_empresa: TextView = itemView.findViewById(R.id.txt_valor_pago_vaga_empresa)
        val img_editar_vaga_empresa: ImageView = itemView.findViewById(R.id.img_editar_vaga_empresa)
        val img_delete_vaga_empresa: ImageView = itemView.findViewById(R.id.img_delete_vaga_empresa)
        val btn_proposta_vaga_empresa: Button = itemView.findViewById(R.id.btn_propostas_vaga_empresa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vaga, parent, false)
        return feedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: feedViewHolder, position: Int) {
        val vaga = vagaList[position]

        Log.d("FeedAdapter", "Nome do Projeto: ${vaga.nomeProjeto}")
        Log.d("FeedAdapter", "Descrição: ${vaga.descricao}")
        Log.d("FeedAdapter", "Habilidades: ${vaga.habilidades}")
        Log.d("FeedAdapter", "Email: ${vaga.email}")
        Log.d("FeedAdapter", "Valor Pago: ${vaga.valorPago}")

        holder.txt_nome_vaga_empresa.text = "Nome do projeto: ${vaga.nomeProjeto}"
        holder.txt_descricao_vaga_empresa.text = "Descrição: ${vaga.descricao}"
        holder.txt_habilidade_vaga_empresa.text = "Habilidades: ${vaga.habilidades}"
        holder.txt_email_vaga_empresa.text = "Email: ${vaga.email}"
        holder.txt_valor_Pago_vaga_empresa.text = "Valor Proposto: R$${vaga.valorPago}"

        holder.btn_proposta_vaga_empresa.setOnClickListener {
            val intent = Intent(context, OfertasEmpresaActivity::class.java)
            intent.putExtra("idVaga", vaga.idVaga)
            intent.putExtra("idUser", vaga.idUser)
            context.startActivity(intent)

        }


        holder.img_editar_vaga_empresa.setOnClickListener {
            val vaga = vagaList[position]
            abrirDialogEditar(vaga)
        }

        holder.img_delete_vaga_empresa.setOnClickListener {
            vagaList.removeAt(position)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, vagaList.size)

            removerItemDoFirestore(vaga.idVaga)
        }
    }

    private fun removerItemDoFirestore(idVaga: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("vagas")
            .document(idVaga)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Item deletado com sucesso!")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao deletar o item", e)
            }
    }

    private fun abrirDialogEditar(vaga: Vaga) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_vaga, null)

        val editNomeProjeto: EditText = dialogView.findViewById(R.id.edit_titulo_vaga_empresa)
        val editDescricao: EditText = dialogView.findViewById(R.id.edit_vaga_descricao_empresa)
        val editHabilidade: EditText = dialogView.findViewById(R.id.edit_vaga_habilidades_empresa)
        val editEmail: EditText = dialogView.findViewById(R.id.edit_vaga_email_empresa)
        val editSalario: EditText = dialogView.findViewById(R.id.edit_vaga_salario_empresa)
        val btnSalvarVaga: Button = dialogView.findViewById(R.id.btn_salvar_edit_vaga)
        val btnCancelarVaga: Button = dialogView.findViewById(R.id.btn_cancelar_edit_vaga)

        editNomeProjeto.setText(vaga.nomeProjeto)
        editDescricao.setText(vaga.descricao)
        editHabilidade.setText(vaga.habilidades)
        editEmail.setText(vaga.email)
        editSalario.setText(vaga.valorPago)

        val dialog = AlertDialog.Builder(context)
            .setTitle("Editar Vaga")
            .setView(dialogView)
            .create()

        btnCancelarVaga.setOnClickListener {
            dialog.dismiss()
        }


        btnSalvarVaga.setOnClickListener {
            val nomeProjeto = editNomeProjeto.text.toString()
            val descricao = editDescricao.text.toString()
            val habilidades = editHabilidade.text.toString()
            val email = editEmail.text.toString()
            val salario = editSalario.text.toString()

            vaga.nomeProjeto = nomeProjeto
            vaga.descricao = descricao
            vaga.habilidades = habilidades
            vaga.email = email
            vaga.valorPago = salario

            atualizarVagaNoFirestore(vaga)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun atualizarVagaNoFirestore(vaga: Vaga) {
        val db = FirebaseFirestore.getInstance()
        vaga.idUser = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        db.collection("vagas")
            .document(vaga.idVaga)  // Usando o ID da vaga para atualizar
            .set(vaga)
            .addOnSuccessListener {
                Log.d("Firestore", "Vaga atualizada com sucesso!")
                notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Erro ao atualizar vaga", e)
            }
    }

    override fun getItemCount(): Int = vagaList.size

    fun updateData(newVagaList: List<Vaga>) {
        vagaList.clear()
        vagaList.addAll(newVagaList)
        notifyDataSetChanged()
    }
}
