package br.com.alugames.models

import transformarEmIdade
import java.util.*
import kotlin.random.Random

data class Gamer(var nome: String, var email: String) {
    var dataDeNascimento: String? = null
    var usuario: String? = null
        set(value) {
            field = value
            if (idInterno.isNullOrBlank()) {
                criarIdInterno()
            }
        }
    var idInterno: String? = null
        private set

    val jogosBuscados = mutableListOf<Jogo?>()

    constructor(nome: String, email: String, usuario: String, dataDeNascimento: String) : this(
        nome,
        email
    ) {
        this.usuario = usuario
        this.dataDeNascimento = dataDeNascimento
        criarIdInterno()
    }

    init {
        if (nome.isNullOrBlank()) {
            throw IllegalArgumentException("Nome esta em branco")
        }
        this.email = validarEmail()
    }

    override fun toString(): String {
        val idade = dataDeNascimento?.transformarEmIdade() ?: 0
        return "Gamer(nome='$nome', " +
                "email='$email', " +
                "dataDeNascimento=$dataDeNascimento, " +
                "usuario=$usuario, " +
                "idInterno=$idInterno, " +
                "Idade= $idade)"
    }

    fun criarIdInterno() {
        val numero = Random.nextInt(10000)
        val tag = String.format("%04d", numero)

        idInterno = "$usuario#$tag"
    }

    fun validarEmail(): String {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (regex.matches(email)) {
            return email
        } else {
            throw IllegalArgumentException("Email inválido")
        }
    }

    companion object {
        fun criarGamer(leitura: Scanner): Gamer {
            println("Boas vindas ao AluGames! Vamos fazer seu cadastro. Digite seu nome: ")
            val nome = leitura.nextLine()
            println("Digite seu email: ")
            val email = leitura.nextLine()
            println("Deseja completar seu cadastro com usuário e data de nascimento? S/N")
            val opcao = leitura.nextLine()
            if (opcao.equals("s", true)) {
                println("Digite seu nome de usuário: ")
                val usuario = leitura.nextLine()
                println("Digite sua data de nascimento(DD/MM/AAAA): ")
                val dataDeNascimento = leitura.nextLine()

                return Gamer(nome, email, usuario, dataDeNascimento)
            } else {
                return Gamer(nome, email)
            }
        }
    }
}