package com.guilhermedelecrode.appmatch.model.empresa

data class Vaga(
    val idVaga: String,
    var idUser: String,
    var nomeProjeto: String,
    var descricao: String,
    var habilidades: String,
    var email: String,
    var valorPago: String
)

