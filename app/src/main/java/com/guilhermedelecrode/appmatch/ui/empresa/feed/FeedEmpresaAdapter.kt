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

class FeedEmpresaAdapter(private val context: Context,
                         private val vagaList: MutableList<Vaga>) : RecyclerView.Adapter<FeedEmpresaAdapter.feedViewHolder>() {


     inner class feedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_nome_projeto_empresa: TextView = itemView.findViewById(R.id.txt_nome_projeto_empresa)
        val txt_descricao: TextView = itemView.findViewById(R.id.txt_descricao_vaga)
        val txt_habilidade: TextView = itemView.findViewById(R.id.txt_habilidades)
        val txt_email : TextView = itemView.findViewById(R.id.txt_email)
        val txt_valor_Pago: TextView = itemView.findViewById(R.id.txt_valor_Pago)
        val img_editar_vaga: ImageView = itemView.findViewById(R.id.img_editar_vaga)
        val img_delete_vaga: ImageView = itemView.findViewById(R.id.img_delete_vaga)
        val btn_proposta: Button = itemView.findViewById(R.id.btn_propostas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vaga, parent, false)
        return feedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: feedViewHolder, position: Int) {
        val vaga = vagaList[position]

        Log.d("FeedAdapter", "Nome do Projeto: ${vaga.nomeProjeto}")
        Log.d("FeedAdapter", "Descrição: ${vaga.descricao}")
        Log.d("FeedAdapter", "Habilidades: ${vaga.habilidades}")
        Log.d("FeedAdapter", "Email: ${vaga.email}")
        Log.d("FeedAdapter", "Valor Pago: ${vaga.valorPago}")

        holder.txt_nome_projeto_empresa.text = "Nome do projeto: ${vaga.nomeProjeto}"
        holder.txt_descricao.text = "Descrição: ${vaga.descricao}"
        holder.txt_habilidade.text = "Habilidades: ${vaga.habilidades}"
        holder.txt_email.text = "Email: ${vaga.email}"
        holder.txt_valor_Pago.text = "Valor Proposto: R$${vaga.valorPago}"

        holder.btn_proposta.setOnClickListener {
            val intent = Intent(context, OfertasEmpresaActivity::class.java)
            intent.putExtra("idVaga", vaga.idVaga)
            intent.putExtra("idUser", vaga.idUser)
            context.startActivity(intent)

        }


        holder.img_editar_vaga.setOnClickListener {
            val vaga = vagaList[position]
            // Abrir o dialog para editar
            abrirDialogEditar(vaga)
        }

        // Configurar o botão de deletar
        holder.img_delete_vaga.setOnClickListener {
            // Remover item da lista
            vagaList.removeAt(position)

            // Notificar o RecyclerView sobre a remoção
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, vagaList.size)

            // Se você estiver usando um banco de dados (como Firestore), também precisaria remover o item do banco
            removerItemDoFirestore(vaga.idVaga)
        }
    }
    private fun removerItemDoFirestore(idVaga: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("vagas")
            .document(idVaga)  // Usando o ID da vaga para localizar e deletar o documento correto
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



        // Preencher os campos com os dados da vaga
        editNomeProjeto.setText(vaga.nomeProjeto)
        editDescricao.setText(vaga.descricao)
        editHabilidade.setText(vaga.habilidades)
        editEmail.setText(vaga.email)
        editSalario.setText(vaga.valorPago)


        // Criar e exibir o dialog
        val dialog = AlertDialog.Builder(context)
            .setTitle("Editar Vaga")
            .setView(dialogView)
            .create()


        btnCancelarVaga.setOnClickListener {
            dialog.dismiss() // Fecha o dialog
        }


        btnSalvarVaga.setOnClickListener {

            // Obter os dados atualizados
            val nomeProjeto = editNomeProjeto.text.toString()
            val descricao = editDescricao.text.toString()
            val habilidades = editHabilidade.text.toString()
            val email = editEmail.text.toString()
            val salario = editSalario.text.toString()

            // Atualizar o objeto Vaga
            vaga.nomeProjeto = nomeProjeto
            vaga.descricao = descricao
            vaga.habilidades = habilidades
            vaga.email = email
            vaga.valorPago = salario

            // Atualizar no Firestore
            atualizarVagaNoFirestore(vaga)

            // Fechar o dialog
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
                // Atualizar a lista no RecyclerView, se necessário
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
