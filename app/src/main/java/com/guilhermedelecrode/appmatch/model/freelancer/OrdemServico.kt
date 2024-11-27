package com.guilhermedelecrode.appmatch.model.freelancer

data class OrdemServico(
    val idOrdemServico: String = "",
    val idVaga: String = "",
    val idUserEmpresa: String = "",
    val idUserFree: String = "",
    val nomeProjeto: String = "",
    val nomeFreelancer: String = "",
    val prazo : String = "",
    val email : String = "",
    var status: String = "Em andamento" // Definindo status padrão como "Em andamento"
)
