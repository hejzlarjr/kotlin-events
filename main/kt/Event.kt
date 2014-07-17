package net.gmc.nuc.extensions.events

import kotlin.properties.Delegates
import java.util.ArrayList

public class Event<eventArg : EventArg> {
    private val handlers = ArrayList<((eventArg) -> Unit)>()

    fun plusAssign(handler: (eventArg) -> Unit) {
        handlers.add(handler)
    }

    fun minusAssign(handler: (eventArg) -> Unit) {
        handlers.remove(handler)
    }

    fun invoke(value: eventArg) {
        for (handler in handlers) handler(value)
    }
}