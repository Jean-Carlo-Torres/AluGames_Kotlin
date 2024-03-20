package br.com.alugames.models

class InfoJogo(val info: InfoApiShark) {
    override fun toString(): String {
        return info.toString()
    }
}