package de.hiorcraft.nex.Lobby

import dev.hiorcraft.nex.base.api.common.state.EventServerState

val eventServerAccess = EventServerAccess()

class EventServerAccess {
    fun getEventServerState(): EventServerState = redisLoader.eventServerState.get()
    fun setEventServerState(state: EventServerState) = redisLoader.eventServerState.set(state)
}
