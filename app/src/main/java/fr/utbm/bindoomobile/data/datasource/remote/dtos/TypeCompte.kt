package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "typeCompte", strict = false)
data class TypeCompte(
    @field:Element(name = "code", required = false)
    var code: String = "",

    @field:Element(name = "libelle", required = false)
    var libelle: String = ""
)
