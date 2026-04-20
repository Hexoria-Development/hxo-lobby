package de.hiorcraft.nex.Lobby.inventory.impl

//fun navigatorInventory() = menu(text("<shift:-48><glyph:server_selector>"), 6) {
//    setOnGlobalDrag { it.cancel() }
//    setOnGlobalClick { it.cancel() }
//
//    staticPane(Slot.fromXY(1, 0), 3, 3) {
//        fillWith(cosmeticsItem)
//
//        setOnClick {
//            val player = it.whoClicked as? Player ?: return@setOnClick
//
//            player.sendText {
//                appendSuccessPrefix()
//                append(text("Du hast den Navigator geöffnet!"))
//            }
//            player.closeInventory()
//        }
//    }
//}
//
//private val cosmeticsItem = plugin.getLobbyItem().apply {
//    displayName {
//        primary("???")
//    }
//}