package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "personneInfo", strict = false)
data class PersonneInfo(
    @field:Element(name = "changerMotDePasse", required = false)
    var changerMotDePasse: Boolean = false,

    @field:Element(name = "code", required = false)
    var code: String = "",

    @field:Element(name = "nom", required = false)
    var nom: String = "",

    @field:Element(name = "prenom", required = false)
    var prenom: String = "",

    @field:Element(name = "telephone", required = false)
    var telephone: String = ""
)
