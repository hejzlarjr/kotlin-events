package net.gmc.nuc.extensions.events

import org.junit.Test
import org.junit.Before
import org.junit.After
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import org.mockito.Mockito.times
import org.mockito.BDDMockito.given
import org.mockito.MockitoAnnotations
import kotlin.properties.Delegates
import net.gmc.nuc.extensions.events.Event
import net.gmc.nuc.extensions.events.EventArg


public class EventTest {

    var event: Event<EventArg> by Delegates.notNull()
    var eventArg: EventArg by Delegates.notNull()
    var function: ((EventArg) -> Unit) by Delegates.notNull()

    Before
    fun setUp() {
        event = Event<EventArg>()
        eventArg = mock(javaClass<EventArg>())!!
        function = mock(javaClass<kotlin.Function1<EventArg, Unit>>())!!
    }

    After
    fun after() {
        event -= function
    }

    Test fun plusAssignOverrideWork() {
        event(eventArg)
        verifyZeroInteractions(function)

        event += function
        event(eventArg)

        verify(function)!!(eventArg)
    }

    Test fun invokeOverrideWork() {
        event += function
        event(eventArg)

        verify(function, times(1))!!(eventArg)
    }

    Test fun minusAssignOverrideWork() {
        event += function
        event(eventArg)

        verify(function, times(1))!!(eventArg)

        event -= function
        event(eventArg)

        verifyZeroInteractions(function)
    }
}