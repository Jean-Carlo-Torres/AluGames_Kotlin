package br.com.alugames.principal

import br.com.alugames.models.Gamer

fun main(){
    val gamer = Gamer("Sam", "sam@email.com")

    gamer.let {
        it.dataDeNascimento = "13/10/2000"
        it.usuario = "sam.uchira"
    }

    println(gamer)
}