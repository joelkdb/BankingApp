package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "sfd", strict = false)
data class Sfd(

    @field:Element(name = "code", required = false)
    var code: String = "",

    @field:Element(name = "libelle", required = false)
    var libelle: String = "",

    @field:Element(name = "logo", required = false)
    var logo: String = "",

    @field:Element(name = "pays", required = false)
    var pays: Pays? = null,

    @field:Element(name = "prefixCompte", required = false)
    var prefixCompte: String = "",
)
