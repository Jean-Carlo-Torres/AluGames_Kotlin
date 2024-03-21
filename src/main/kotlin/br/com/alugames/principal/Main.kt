package br.com.alugames.principal

import br.com.alugames.models.Gamer
import br.com.alugames.models.Jogo
import br.com.alugames.services.ConsumoApi
import java.util.*

val leitura = Scanner(System.`in`)
val gamer = Gamer.criarGamer(leitura)

fun main() {
    println("Cadastro realizado com sucesso!")
    println(gamer)
    menu()
}

fun menu() {
    var menu = """
    [1] Buscar jogos
    [2] Listar jogos
    [3] Remover jogo da lista
"""
    println(menu)
    val opcao = leitura.nextInt()

    when (opcao) {
        1 -> buscarJogos()
        2 -> listarJogos()
        3 -> removerJogo()
        else -> {
            println("Opção inválida. Tente novamente.")
        }
    }
}

fun retornarAoMenu() {
    println()
    println("Digite qualquer tecla para retornar ao menu")
    leitura.nextLine()
    menu()
}

fun buscarJogos() {
    do {
        println("Digite um código de jogo para buscar:")
        val busca = leitura.nextInt()
        leitura.nextLine() // Consumir nova linha pendente

        val buscaApi = ConsumoApi()
        val informacaoJogo = buscaApi.buscaJogo(busca.toString())

        var meuJogo: Jogo? = null

        val resultado = runCatching {
            meuJogo = Jogo(
                informacaoJogo.info.title,
                informacaoJogo.info.thumb
            )
        }
        resultado.onFailure {
            println("Jogo inexistente. Tente outro id.")
        }

        resultado.onSuccess {
            println("Deseja inserir uma descrição personalizada? S/N")
            val opcao = leitura.nextLine()
            if (opcao.equals("s", true)) {
                println("Insira a descrição personalizada para o jogo:")
                val descricaoPersonalizada = leitura.nextLine()
                meuJogo?.descricao = descricaoPersonalizada
            } else {
                meuJogo?.descricao = meuJogo?.titulo
            }
            gamer.jogosBuscados.add(meuJogo)
        }

        println("Deseja buscar um novo jogo? S/N")
        val resposta = leitura.nextLine()
    } while (resposta.equals("s", true))
    retornarAoMenu()
}


fun listarJogos() {
    println("Lista de jogos buscados: ")
    println("---------------------------------")
    if (gamer.jogosBuscados.isNotEmpty()) {
        gamer.jogosBuscados.sortBy {
            it?.titulo
        }

        gamer.jogosBuscados.forEach {
            println("Título: " + it?.titulo)
        }
        println("---------------------------------")
        println("Busca finalizada com sucesso.")
    } else {
        println("A lista esta vazia!")
    }
    leitura.nextLine()
    retornarAoMenu()
}

fun removerJogo() {
    println("Informe a posição do jogo que deseja remover: ")
    if (gamer.jogosBuscados.isNotEmpty()) {
        val posicao = leitura.nextInt()
        gamer.jogosBuscados.removeAt(posicao)
        println("Removido com sucesso")
    } else {
        println("A lista já esta vazia!")
    }
    leitura.nextLine()
    retornarAoMenu()
}

