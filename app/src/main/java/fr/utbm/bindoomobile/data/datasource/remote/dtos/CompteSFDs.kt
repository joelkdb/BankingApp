package fr.utbm.bindoomobile.data.datasource.remote.dtos

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "compteSFDs")
data class CompteSFDs(
    @field:ElementList(name = "compteSFDs", entry = "compte", inline = true, required = false)
    var compteSFDs: MutableList<Compte> = mutableListOf()
)
