// Akka Actor Model Benefits
  1) Event-driven model:          (i.e. actor allows non-blocking tasks)
     actors perform work in response to messages
     communication between actors is asynchronous, allowing actors to send messages and continue their own work without blocking to wait for a reply
     i.e. the sender does not stick around waiting for their message to be processed by the recipient
          instead, the sender puts the message in the recipient mailbox and is free to do other work
     actor mailbox:
       a message queue with ordering semantics
       the order of multiple messages sent from the same Actor is preserved, but can be interleaved with messages sent by another Actor
  2) Strong isolation principles: (i.e. no shared state between actors)
     unlike regular objects in Scala, an actor does not have a public API in terms of methods that you can invoke
     instead, its public API is defined through "messages" that the actor handles
     this prevents any sharing of state between actors (just like no static fields shared by class instances)
     the only way to observe another actor’s state is by sending it a message asking for it
  3) Location transparency:       (i.e. fault tolerance)
     the system constructs actors from a factory and returns references to the instances
     in Akka, location does not matter:
       location transparency means that the ActorRef can represent an instance of the running actor in-process or on a remote machine
       therefore, the runtime can optimize the system by changing an Actor location or the entire application topology while it is running
       moreover, actor instances can start, stop, move, and restart to scale up and down as well as recover from unexpected failures
       i.e. the system can heal itself by crashing faulty Actors and restarting healthy ones.
  4) Lightweight:
     each instance consumes only a few hundred bytes, which realistically allows millions of concurrent Actors to exist in a single application
     when an actor is not processing messages, it is in a suspended state in which it does not consume any resources apart from memory

// Defining Actors and messages
  1) Since messages are like actor public API, it is a good practice to define messages with semantic and domain specific meaning even if they are just data type wrapper
  2) Messages should be immutable objects, since they are shared between different threads
  3) It is a good practice to put an actor associated messages in its companion object
     This makes it easier to understand what type of messages the actor expects and handles
  4) It is also a common pattern to use a props method in the companion object that describes how to construct the Actor
ex.
object Greeter {
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor)) // this actor requires two fields when constructed
  final case class WhoToGreet(who: String) // this actor handles message type: WhoToGreet (a data-type wrapper with a semantic name)
  case object Greet                        // this actor handles message type: Greet      (a data-type wrapper with a semantic name)
}
// The props() method creates and returns a akka.actor.Props instance
//   Props is a configuration object used to create an Actor: ex. ActorRef actor = system.actorOf(Props.create(Greeter.class))
//   Props is a configuration class to specify options for the creation of actors
//   think of it as an immutable and thus shareable recipe for creating an actor that can include associated deployment information
//   this example simply passes the parameters that the Actor requires when being constructed

class Greeter(message: String, printerActor: ActorRef) extends Actor { // the Greeter has two immutable fields: a message: String, and a printerActor: ActorRef
  import Greeter.{WhoToGreet, Greet}
  import Printer.Greeting                                     // the Printer actor handles message type: Greeting

  var greeting = ""                                           // the actor internal state (mutable, but thread-safe) 

  def receive = {
    case WhoToGreet(who) => greeting = s"$message, $who"      // this mutates the actor internal state
    case Greet           => printerActor ! Greeting(greeting) // this asks the actor to do the Greet task (by telling its printerActor with its the internal state)
  }
}
// Greeter class extends the akka.actor.Actor trait and implements the receive method
// Mutating the internal state of an Actor is fully thread safe since it is protected by the Actor model

object Printer {
  def props: Props = Props[Printer]
  final case class Greeting(greeting: String)
}

class Printer extends Actor with ActorLogging {
  import Printer.Greeting

  def receive = {
    case Greeting(greeting) =>
      log.info(s"Greeting received (from ${sender()}): $greeting") // the logger: log is provided by extending ActorLogging
  }
}
// actor Printer extends akka.actor.ActorLogging to automatically get a reference to a logger

// Creating the Actors
//   in Akka you cannot create an instance of an Actor using the new keyword
//   instead, you create Actor instances using a factory (i.e. akka.actor.ActorSystem, similar to Spring BeanFactory)
//    the factory does not return an actor instance, but a reference, akka.actor.ActorRef, that points to the actor instance
//    the factory acts as a container for Actors and manages their life-cycles
//    the actorOf() factory method creates Actors and takes two parameters: a configuration object called Props and a name

// Create the 'helloAkka' actor system
val system: ActorSystem = ActorSystem("helloAkka")

// Create the printer actor
val printer: ActorRef = system.actorOf(Printer.props, "printerActor")
// Create three greeter actors, each with a specific greeting message
val howdyGreeter: ActorRef   = system.actorOf(Greeter.props("Howdy", printer),    "howdyGreeter"  )
val helloGreeter: ActorRef   = system.actorOf(Greeter.props("Hello", printer),    "helloGreeter"  )
val goodDayGreeter: ActorRef = system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")

// Sending messages
howdyGreeter ! WhoToGreet("Akka")
howdyGreeter ! Greet

howdyGreeter ! WhoToGreet("Lightbend")
howdyGreeter ! Greet

helloGreeter ! WhoToGreet("Scala")
helloGreeter ! Greet

goodDayGreeter ! WhoToGreet("Play")
goodDayGreeter ! Greet
