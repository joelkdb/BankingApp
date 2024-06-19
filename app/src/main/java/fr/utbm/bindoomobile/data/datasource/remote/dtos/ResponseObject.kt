package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class ResponseObject(
    @field:Element(name = "code", required = false)
    var code: Int = -1,

    @field:Element(name = "value", required = false)
    var value: String = "",

    @field:Element(name = "description", required = false)
    var description: String = "",

    @field:Element(name = "personneInfo", required = false)
    var personneInfo: PersonneInfo? = null,

    @field:ElementList(name = "comptes", entry = "compte", required = false)
    var comptes: List<Compte>? = null,

    @field:ElementList(name = "statements", entry = "statement", required = false)
    var statements: ArrayList<Statement>? = null,

    @field:Element(name = "member", required = false)
    var member: MFIMember? = null,
)
