#Events in Kotlin
###Kotlin 0.7+

This article shows how to declare, invoke and hook up events in Kotlin. 
Events allow you to define what functions (lambdas) 

are called when code triggers certain “actions”.
that will be called upon the occurrence of some "actions" in your code.

 There can be more than one method registered to an event. These methods are called when your code indicates that the event has occurred. Events can be used in traits.

##Syntax:
```
[modifiers] implicitlyTypedDeclarations member-name: Event<YourEventArgs>
```

#####*modifiers* (optional)
  Modifiers that include:
  * `override`
  * `public`
  * `protected`
  * `open`
  * `final`
  * `private`
	

#####*implicitlyTypedDeclarations*
Defines whether variables are marked as immutable. These variables cannot be reassigned values (objects). However, individual objects can be used normally. Immutability is often desirable as it protects you from unwanted side effects. Available values are `var`/`val`. 


#####*member-name*
The name of the event.


#####*yourEventArgs*
This class contains the event’s arguments, e.g. for `MouseEventArgs`, it contains the X and Y coordinates, details about pressed keys; similarly, events triggered by jobs created during processing can contain IDs or other details before finished objects are returned.


`var event: Event<YourEventArg>`
Using reassociable values is often not desirable as, generally, there is no need to change objects with listeners registered to them. The only case in which they are used is in conjunction with the notNull delegate. In this case, values are assigned only once.
 
`val event: Event<YourEventArg>`
Once objects are assigned, they cannot be reassigned (**recommended**).
`event = Event<EventArg>()`

Values can be initialized when declaring class properties / function variables:
`val  event = Event<EventArg>()`

or delegates can be used with class properties:
`var event: Event<EventArg> by Delegates.notNull()`

Such values are then not nullable. If they have not been initialized yet and they are accessed, the
`java.lang.IllegalStateException: “Property event should be initialized before get”` is triggered. Value can be initialized in a different part of the code, e.g. they can all be placed in the constructor or method:
      fun initialize() {
              event = Event<EventArg>()
          }



#####To connect to event, use the operator `+=`. 
      // Add "list changed logic" to the Changed event on "list":
      list.Changed += { … }
#####When the client code is done receiving event invocations, it disconnects from the event by using the operator `-=`. 
      // Disconnect from the event:
      list.Changed -= { … }




##To use events:
######1.	Declare an object with event args. 
   * a.	If creating your event args object, create a child class of the `EventArgs`. This object will carry data of the action that invokes the event.
   * b.	If you want to use the existing event args, you only need to know its name.

######2.	Declare an event.
Create a class that defines the event. This can be a property of type `Event<MyEventArgs>`.
######3.	Invoke an event.
Use the event, trigger it. You do not need to check if there are listeners registered to the event (e.g. like in .NET 
`if (MyEvent! = null)
         MyEvent();`
)
You can also call `MyEvent()` safely.
######4.	Hook up to an event
Define one or more classes that consume your events.
   * a.	Assign one or more methods using the `+=` and `-=` operators.
   * b.	Optionally, you can write your code directly in the form of lambda functions.

######5.	Enjoy.
	


##In the code:
######1.	Declare an object with event args
        public class MyEventArgs(val message: String) : EventArg {
        }
######2.	Declare an event
        val myEvent = Event<MyEventArgs>()
######3.	Invoke an event
        myEvent(MyEventArgs("Hello word"))
######4.	Hook up to an event
        someClass.myEvent += { (e) -> println(e.message) }
        	*If you want to keep the function reference (for future unregistering), you can use:
        	    val myFunction = { (e: MyEventArgs): Unit -> println(e.message) }
                someClass.myEvent += myFunction
                someClass.myEvent -= myFunction
######5.	Enjoy
        val myFunction = { (e: MyEventArgs): Unit -> println(e.message) }
        someClass.myEvent += myFunction
        
        In “someClass”, use the event, i.e. trigger it:
        myEvent(MyEventArgs("Hello word"))


**External code cannot access or change the main list of the event handler as the only available methods are += and -=.**

*This event is not thread-safe, i.e. it cannot be used for cross thread operations. In extreme cases, you can create a concurentEvent and use a thread-safe collection e.g. ConcurrentHashMap, in place of ArrayList.*
        val handlers = ArrayList<((eventArg) -> Unit)>()
        
        
        
        
=============================================
A GMC Software Technology Original Production,
* http://www.gmc.net
* http://gmchk.cz/