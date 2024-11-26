package com.guilhermedelecrode.appmatch.model.empresa

data class Vaga(
    val idVaga: String,
    var idUser: String, //Id do usuario que criou a vaga
    var id : String, // id do freelancer que envivou a oferta
    var nomeEmpresa : String,
    var nomeProjeto: String,
    var descricao: String,
    var habilidades: String,
    var email: String,
    var valorPago: String

){
    constructor() : this("", "", "", "", "", "", "", "", "")

}


