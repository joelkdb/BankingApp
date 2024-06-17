package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "pays", strict = false)
data class Pays(
    @field:Element(name = "code", required = false)
    var code: String="",

    @field:Element(name = "codeISO", required = false)
    var codeISO: String="",

    @field:Element(name = "libelle", required = false)
    var libelle: String="",

    @field:Element(name = "libelleNationalite", required = false)
    var libelleNationalite: String=""

)
