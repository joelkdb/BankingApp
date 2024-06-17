package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "compte", strict = false)
data class Compte(
    @field:Element(name = "abonnementActif", required = false)
    var abonnementActif: Boolean = true,

    @field:Element(name = "agence", required = false)
    var agence: Agence? = null,

    @field:Element(name = "code", required = false)
    var code: String = "",

    @field:Element(name = "codeDevise", required = false)
    var codeDevise: String = "",

    @field:Element(name = "intituleCompte", required = false)
    var intituleCompte: String = "",

    @field:Element(name = "numero", required = false)
    var numero: String = "",

    @field:Element(name = "prelevementOk", required = false)
    var prelevementOk: Boolean = true,

    @field:Element(name = "typeCompte", required = false)
    var typeCompte: TypeCompte? = null,

    @field:Element(name = "typeMembre", required = false)
    var typeMembre: TypeMembre? = null,

//    @field:Element(name = "numeroIdMarchand", required = false)
//    var numeroIdMarchand: String
)
