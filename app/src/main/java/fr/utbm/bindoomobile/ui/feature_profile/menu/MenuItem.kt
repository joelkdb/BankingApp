package fr.utbm.bindoomobile.ui.feature_profile.menu

import fr.utbm.bindoomobile.ui.core.resources.UiText


sealed class MenuItem {
    data class Item(
        val entry: MenuEntry,
        val onClick: (MenuEntry) -> Unit = {}
    ) : MenuItem()

    data class Section(
        val title: UiText
    ) : MenuItem()
}