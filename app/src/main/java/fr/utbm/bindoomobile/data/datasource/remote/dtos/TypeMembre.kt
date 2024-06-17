package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "typeMembre", strict = false)
data class TypeMembre(
    @field:Element(name = "code", required = false)
    var code: String = "",

    @field:Element(name = "libelle", required = false)
    var libelle: String = ""
)
