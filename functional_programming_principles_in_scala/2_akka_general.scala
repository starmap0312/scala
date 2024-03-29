// Actor Architecture
  1) The Akka actor hierarchy:
     both ActorSystem and ActorContext extend ActorRefFactory
  1.1) interface implemented by ActorSystem and ActorContext, the only two places from which you can create new actors

  trait ActorRefFactory {
    def actorOf(props: Props): ActorRef      // create a new actor as child of this context
    def actorOf(props: Props, name: String): ActorRef
  }

  1.2) abstract class ActorSystem extends ActorRefFactory { ... }
  ex.
  val system = ActorSystem("testSystem")     // we need to instantiate an ActorSystem to create top-level actors
  val actorRef = system.actorOf(Props[MyActorRefActor], "top-level-actor") // ex. Actor[akka://testSystem/user/top-level-actor//1053618476]

  1.3) trait ActorContext extends ActorRefFactory { ... }
  ex.
  class PrintMyActorRefActor extends Actor { // class Actor has an implicit val context: ActorContext
    override def receive: Receive = {
      case "createActor" =>                  // ActorContext can be used inside an Actor to create non-top-level actors
        val actorRef = context.actorOf(Props.empty, "non-top-level-actor") // ex. Actor[akka://testSystem/user/first-actor/non-top-level-actor//-1544706041]
      case "stop" => context.stop(self)      // actor stops itself when receving the stop message (don't stop another actor, send message to let it stop itself instead)
    }
  }

  2) The Akka actor lifecycle:
  2.1) whenever an actor is stopped, all of its children are recursively stopped (this avoids resource leaks, ex. open sockets and files)
       to stop an actor, call "context.stop(self)" inside an Actor class
  2.2) you can add lifecycle hooks by overridding methods in an actor implementation, ex. preStart() and postStop()
       
  3) Failure handling:
  3.1) whenever an actor fails (throws an exception or an unhandled exception bubbles out from receive) it is temporarily "suspended"
       the failure information is propagated to the parent, which then decides how to handle the exception caused by the child actor
       i.e. parents act as supervisors for their children
  3.2) the default supervisor strategy is to "stop and restart the child", i.e. all failures result in a restart by default

// Akka Dispatchers vs. Java Executor framework
  1) Akka Dispatcher:
     i) Akka dispatcher is based on the Java Executor framework (part of java.util.concurrent)
        Java Executor provides the framework for the execution of asynchronous tasks
        it is based on the "producer–consumer model":
          the act of task submission (producer) is decoupled from the act of task execution (consumer)
          the threads that submit tasks are different from the threads that execute the tasks
    ii) it has a collaborator ExecutorServiceFactoryProvider, which can be used to create ExecutorServiceFactory, which
          can be used to create a Java "ExecutorService", used to execute a Runnable (i.e. a asynchronous task)
   iii) you may think of Akka dispatcher as an Executor used to create ExecutorService
        depending on the nature of actors, there are two types of ExecutorService created by Akka dispatcher
        a) ForkJoinPool (most common), or
        b) ThreadPoolExecutor (ex. FixedThreadPool or CachedThreadPool) for I/O tasks
  2) Thread pools (i.e. ExecutorService) created by Akka dispatcher
     i) java.util.concurrent.ForkJoinPool:
        the idea is to divide a large task into smaller tasks whose solutions are then combined for the final answer
        * this maximizes the "throughput" of processor cores
        * tasks need to be independent to be able run in parallel
        * "work stealing": threads in the pool will execute tasks created by other active tasks (pending thread execution)
    ii) java.util.concurrent.ThreadPoolExecutor:
        the idea is to create a shared pool of worker threads
        * this minimizes the "overhead" of allocation/deallocation of threads
        * tasks are assigned to the pool using a queue
        * if the number of tasks exceeds the number of threads, then the tasks are queued up until any thread in the pool is available
  3) java.util.concurrent.ThreadPoolExecutor and java.util.concurrent.ForkJoinPool
     "ThreadPoolExecutor" extends AbstractExecutorService extends ExecutorService extends Executor
     "ForkJoinPool"       extends AbstractExecutorService extends ExecutorService extends Executor
     i) "Executor":
        // it is an object that executes submitted Runnable tasks
        public interface Executor {
          void execute(Runnable command);
        }
    ii) "ExecutorService" extends "Executor":
        // it is an Executor that provides methods to manage termination and
        // methods that can produce a Future for tracking progress of one or more asynchronous tasks
        // it can be shut down, which will cause it to reject new tasks
        // upon termination, it has no tasks actively executing, no tasks awaiting execution, and no new tasks can be submitted
        public interface ExecutorService extends Executor {
            void shutdown();
            <T> Future<T> submit(Callable<T> task);
        }
   iii) "ThreadPoolExecutor" extends "AbstractExecutorService" extends "ExecutorService":
        // it is an ExecutorService that executes each submitted task using one of possibly several pooled threads
        // Thread pools provide improved performance when executing large numbers of asynchronous tasks
        // they provide a means of bounding and managing the resources, including threads, consumed when executing a collection of tasks
        public class ThreadPoolExecutor extends AbstractExecutorService {
            public void execute(Runnable command) {
                ... implementation ...
            }
        }
        // you can construct FixedThreadPool  (using Executors.newFixedThreadPool) or
        //                   CachedThreadPool (using Executors.newCachedThreadPool)

        "ForkJoinPool" extends "AbstractExecutorService" extends "ExecutorService":
        // it is an ExecutorService for running ForkJoinTasks
        // it provides the entry point for submissions from non-ForkJoinTask clients, as well as management and monitoring operations
        public class ForkJoinPool extends AbstractExecutorService {
            public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task) {
                ... implementation ...
            }
        }
        // suitable for recursive calculations

// Akka Dispatcher
  1) in Akka, the dispatcher controls and coordinates the message dispatching to the actors mapped on the underlying threads
     they make sure that the resources (threads) are optimized and messages (in mailbox) are processed (by actors) as fast as possible
  2) Akka provides multiple dispatch policies: customized based on number of cores or memory available and application workload type
     Threads are the underlying resources, which can be optimized based on the available CPU cores and application workload type
  3) types of Akka dispatchers:
     i) Dispatcher (default):
        an event-based dispatcher that binds a set of actors to a thread pool backed up by a "BlockingQueue"
        * "every actor has its own mailbox"                                     (i.e. possibly unbalanced load)
        * "all actors share threads from the same thread pool"                  (i.e. better throughput for non-blocking operations)
        * the dispatcher is backed by either a ThreadPoolExecutor or ForkJoinPool (w/ work stealing) executor
    ii) Pinned dispatcher:
        the dispatcher dedicates a unique thread for each actor using the thread
        the dispatcher is useful when the actors are doing I/O operations or performing long-running calculations
        * "every actor has its own mailbox"                                     (i.e. possibly unbalanced load)
        * "each actor has a dedicated thread from its own thread pool"          (i.e. good for blocking operations)
          ex. if the code is making I/O calls or database calls, then such actors will wait until the task is finished
              for such blocking operation, the pinned dispatcher performs better than the default dispatcher
        * the dispatcher is backed by a ThreadPoolExecutor
    ii) Balancing dispatcher:
        an event-based dispatcher that redistributes work from busy actors and allocate work to idle ones
          i.e. the dispatcher looks for idle actors and dispatches the messages to them for processing
        task redistribution is similar to "work stealing" of "fork join pool"
        * "all actors share the same mailbox"                                   (i.e. possibly balanced load)
        * "all actors of the same type share threads from the same thread pool" (i.e. better throughput for non-blocking operations)
        * the dispatcher is backed by a either a ThreadPoolExecutor or ForkJoinPool (w/ work stealing) executor
  4) mailboxes are related to "queue" implementation from the Java concurrent package
     types of queues:
     i) Blocking queue:
        a queue that waits for space to become available before putting in an element
        a queue that waits for the queue to become non-empty before retrieving an element
    ii) Bounded queue:
        queue that limits the size of the queue (i.e. you cannot add more elements than the specified size)
     types of mailbox:
     i) Unbounded mailbox         : java.util.concurrent.ConcurrentLinkedQueue (non-Blocking, non-Bounded)
    ii) Bounded mailbox           : java.util.concurrent.LinkedBlockingQueue   (    Blocking,     Bounded)
   iii) Unbounded priority mailbox: java.util.concurrent.PriorityBlockingQueue (    Blocking, non-Bounded)
    iv) Bounded priority mailbox  : java.util.concurrent.PriorityBlockingQueue (    Blocking,     Bounded)

// Akka Pool
  0) trait RouterConfig extends Serializable {
       def createRouter(system: ActorSystem): Router // create the actual router, which belongs to some ActorSystem and is responsible for routing messages to routees
     }
     a router factory: it produces the actual "router actor" and creates the "routing table"
       routing table is a function which determines the recipients for each message to be dispatched
  1) trait Pool extends RouterConfig {
       def props(routeeProps: Props): Props = routeeProps.withRouter(this) // the supplied Props for the routees created by the router
     }
     a RouterConfig (router factory) for router actor that creates routees as child actors and removes them from the router if they terminate
     it can also decorate a routee Props (a configuration object used in creating an routee actor) with a Router
  2) final case class RandomPool extends Pool {
       override def createRouter(system: ActorSystem): Router = new Router(RandomRoutingLogic())
     }
     a router pool that creates a Router with a RandomRoutingLogic which "randomly" selects one of the target routees to send a message to
  3) final case class BalancingPool extends Pool {
       override def createRouter(system: ActorSystem): Router = new Router(BalancingRoutingLogic())
     }
     a router pool that creates a Router with a BalancingRoutingLogic which "redistribute work" from busy routees to idle routees (all routees share the same mailbox)

// Why modern systems need a new programming model: Akka actor model?
  1) The challenge of encapsulation:
     objects can only guarantee encapsulation (protection of invariants) in the face of single-threaded access
       multi-thread execution easily leads to corrupted internal state
       every invariant can be violated by having two contending threads in the same code segment
     locks are inefficient and easily lead to deadlocks
     locks work locally, attempts to make them distributed exist, but offer limited potential for scaling out
  2) The illusion of shared memory on modern computer architectures:
     there is no real shared memory anymore:
       on modern architectures, CPUs are writing to "cache lines" instead of writing to memory directly
         i.e. most of these caches are local to the CPU core (writes by one core are not visible by another)
       marking variables as "volatile" or "atomic" is costly
         it stalls the cores from doing additional work, and result in bottlenecks on the cache coherence
     CPU cores pass chunks of data (cache lines) explicitly to each other
       "passing messages" is the norm for both CPUs or networked computers
       instead of hiding message passing by marking variables as volatile or atomic,
         a more disciplined approach is to "keep state local" to a concurrent entity and "pass messages" between them
  3) The illusion of a call stack:
     threads delegate tasks among each other without blocking
       we need to handle service faults and recover from them
       i.e. we need explicit error signaling mechanisms (call stack-based error handling)
     tasks/messages might be lost and responses might be delayed arbitrarily
       we can use timeouts to handle response delays, just like networked/distributed systems
       ex. delays caused by a long queue, or by garbage collection, etc.

// How the Actor Model Meets the Needs of Modern, Distributed Systems
  1) usage of message passing avoids locking and blocking
     difference between passing messages and calling methods:
       messages have no return value
       method calls transfer execution, whereas message passing does NOT transfer execution
     actors execute independently from the senders of a message and react to incoming messages sequentially, one at a time
  2) what happens when an actor receives a message
     several actors may add messages to the end of a queue
     a (hidden) scheduler entity takes a worker actor and starts executing it
       if an actor was not scheduled for execution, it is marked as ready to execute
     the worker actor picks the message from the front of the queue
       it may may modify its internal state or send messages to other actors
     the worker actor is then unscheduled
  3) actors need to have
     an address
     a mailbox
       the queue where messages end up
     states (ex. internal variables, etc.)
       the states of actors are local and not shared
       modifying the internal state of an actor is only possible via messages
     messages
       pieces of data representing a signal, similar to method calls and their parameters
     an execution environment
       the machinery that takes actors that have messages to react to and invokes their message handling code
  4) actors handle error situations gracefully
     case 1: when the delegated task on the worker actor failed due to an error in the task (ex. some validation issue like a non-existent user ID)
       the woker actor should reply to the sender with a message, notifying the error case (errors are part of the domain)
     case 2: the worker actor itself encounters an internal fault
       Akka enforces that all actors are organized into a tree-like hierarchy (i.e. an actor that creates another actor becomes the "parent" of that new actor)
         i.e. there is always a responsible entity for managing an actor
       just like with processes, when an actor fails, its parent actor is notified and it can react to the failure
         a supervisor (parent actor) can decide to restart its child actors on certain types of failures or stop them completely
       if the parent actor (supervisor) is stopped, all of its children are recursively stopped too
       
// Overview of Akka libraries and modules
  1) core Akka library: "akka-actor"
     communication with actors is not via method calls but by passing messages
     unlike objects, actors encapsulate not only their state but their execution
     the actor model handles concurrency and distribution at the fundamental level instead of ad hoc features like OOP (ex. locks, error handling etc.)
  2) Remoting module: it solves the problem of addressing and communicating with components of remote systems
     actors can live on different computers and to seamlessly exchange messages
     you can enable remoting with configuration and it has only a few APIs
     a remote and local message are sent exactly the same way
     Challenges of Remoting:
       how to address actor systems living on remote hosts
       how to address individual actors on remote actor systems
       how to turn messages to bytes on the wire
       how to manage low-level, network connections (and reconnections) between hosts, detect crashed actor systems and hosts, transparently
       how to multiplex communications from an unrelated set of actors on the same network connection, transparently
  3) Cluster module: it gives you the ability to organize the components into a meta-system tied together by a membership protocol
     Clustering provides an additional set of services on top of Remoting
     Challenges of Cluster:
       how to maintain a set of actor systems (a cluster) that can communicate with each other and consider each other as part of the cluster
       how to introduce a new system safely to the set of already existing members
       how to reliably detect systems that are temporarily unreachable
       how to remove failed hosts/systems (or scale down the system) so that all remaining members agree on the remaining subset of the cluster
       how to distribute computations among the current set of members
       how do I designate members of the cluster to a certain role
  4) Cluster Sharding module: it solves the problem of distributing a set of actors among members of an Akka cluster
     Challenges of Sharding:
       how to model and scale out a large set of stateful entities on a set of systems
       how to ensure that entities in the cluster are distributed properly so that load is properly balanced across the machines
       how to ensure migrating entities from a crashed system without losing the state
       how to ensure that an entity does not exist on multiple systems at the same time and hence kept consistent
  5) Cluster Singleton module: a single entity responsible for a given task which is shared among other members of the cluster and migrated if the host system fails
     Challenges of Cluster Singleton:
       how to ensure that only one instance of a service is running in the whole cluster
       how to ensure that the service is up even if the system hosting it currently crashes or shut down during the process of scaling down
       how to reach this instance from any member of the cluster assuming that it can migrate to other systems over time
  6) Cluster Publish-Subscribe module: it solves the problem of distributing messages to a set of interested systems in a cluster
     broadcast messages to all subscribers of a topic or send a message to an arbitrary actor that has expressed interest
     Challenges of Cluster Publish-Subscribe
       how to broadcast messages to an interested set of parties in a cluster
       how to send a message to a member from an interested set of parties in a cluster
       how to subscribe and unsubscribe for events of a certain topic in the cluster
  7) Distributed Data module: it provides infrastructure to share data between nodes in an Akka Cluster
     Challenges of Distributed Data:
       how to accept writes even in the face of cluster partitions
       how to share data while at the same time ensuring low-latency local read and write access
  8) Streams module: it is an implementation of the Reactive Streams standard
     when we need to process a potentially large, or infinite, stream of sequential events
       we need to properly coordinate resource usage so that faster processing stages does not overwhelm slower ones in the chain or graph
     Challenges of Streams:
       how to handle streams of events or large datasets with high performance, exploiting concurrency and keep resource usage tight
       how to assemble reusable pieces of event/data processing into flexible pipelines
       how to connect asynchronous services in a flexible way to each other, and have good performance
       how to provide or consume Reactive Streams compliant interfaces to interface with a third party library
  9) HTTP module: it gives a set of tools to create HTTP services and a client that can be used to consume other services
     it is particularly suited to streaming in and out a large set of data or real-time events by leveraging the underlying model of Akka Streams
     Challenges of HTTP:
       how to expose services of a system or cluster to the external world via an HTTP API in a performant way
       how to stream large datasets in and out of a system using HTTP
       how to stream live events in and out of a system using HTTP

// UntypedActor vs. TypedActors
  1) TypedActors have a static interface
     the invocations of the methods on that interface is transformed into message sends
     i.e. each method dispatch is turned into a message that is put on a queue to be processed by TypedActors sequentially one by one
  2) UntypedActors can receive any message
