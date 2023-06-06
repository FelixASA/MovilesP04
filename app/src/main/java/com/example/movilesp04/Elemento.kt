package com.example.movilesp04

class Elemento(simbolo: String, nombre: String, numeroA: Int, pesoA: String, estadosO: String, estado: String, grupo: String) {
    private var simbolo: String
    private var nombre: String
    private var numeroAtomico: Int
    private var pesoAtomico: String
    private var estadosDeoxidacion: String
    private var estado: String
    private var grupo: String

    init {
        this.simbolo = simbolo
        this.nombre = nombre
        this.numeroAtomico = numeroA
        this.pesoAtomico = pesoA
        this.estadosDeoxidacion = estadosO
        this.estado = estado
        this.grupo = grupo
    }

    fun getSimbolo(): String{
        return this.simbolo
    }

    fun getNombre(): String{
        return this.nombre
    }

    fun getNumeroAtomico(): Int{
        return this.numeroAtomico
    }

    fun getPesoAtomico(): String{
        return this.pesoAtomico
    }

    fun getEstadosDeOxidacion(): String{
        return this.estadosDeoxidacion
    }

    fun getEstado(): String{
        return this.estado
    }

    fun getGrupo(): String{
        return this.grupo
    }

    fun isMetal(): Boolean{
        return when(this.grupo){
            "nonmetal" -> false
            "noble gas" -> false
            "halogen" -> false
            "alkali metal" -> true
            "alkaline earth meta" -> true
            "transition metal" -> true
            "post-transition meta" -> true
            "metalloid" -> true
            "lanthanoid" -> true
            "metal" -> true
            "actinoid" -> true
            else -> false
        }
    }

}