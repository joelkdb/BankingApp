package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "member", strict = false)
data class MFIMember(
    @field:Element(name = "lastName", required = false)
    var lastName: String = "",

    @field:Element(name = "firstName", required = false)
    var firstName: String = "",

    @field:Element(name = "accountType", required = false)
    var accountType: String = "",

    @field:Element(name = "balance", required = false)
    var balance: Int = 0,

    @field:Element(name = "phone", required = false)
    var phone: String = "",

    @field:Element(name = "currencyCode", required = false)
    var currencyCode: String = "",

    @field:Element(name = "birthDateText", required = false)
    var birthDateText: String = "",

    @field:Element(name = "gender", required = false)
    var gender: String = "",

    @field:Element(name = "address", required = false)
    var address: String = "",

    @field:Element(name = "phoneCode", required = false)
    var phoneCode: String = "",
) {
    val fullName
        get() = "$firstName $lastName"
}
