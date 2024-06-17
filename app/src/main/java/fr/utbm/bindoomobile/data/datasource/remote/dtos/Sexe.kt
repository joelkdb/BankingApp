package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "sexe", strict = false)
data class Sexe(
    @field:Element(name = "code")
    var code: String,
    @field:Element(name = "libelle")
    var libelle: String
)