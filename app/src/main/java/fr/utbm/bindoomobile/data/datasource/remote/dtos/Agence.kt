package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "agence", strict = false)
data class Agence(

    @field:Element(name = "code", required = false)
    var code: String = "",

    @field:Element(name = "codeSIG", required = false)
    var codeSIG: String = "",

    @field:Element(name = "email", required = false)
    var email: String = "",

    @field:Element(name = "latitude", required = false)
    var latitude: Double = 0.0,

    @field:Element(name = "libelle", required = false)
    var libelle: String = "",

    @field:Element(name = "localite", required = false)
    var localite: String = "",

    @field:Element(name = "longitude", required = false)
    var longitude: Double = 0.0,

    @field:Element(name = "sfd", required = false)
    var sfd: Sfd? = null,

    @field:Element(name = "telephone", required = false)
    var telephone: String = "",

//    @field:Element(name = "codeAgentCredit", required = false)
//    val codeAgentCredit: String,
)
