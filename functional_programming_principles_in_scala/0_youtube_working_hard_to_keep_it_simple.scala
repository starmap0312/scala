// Parallelism vs. Concurrency
// 1) Parallel programming  :
//    execute programs faster on parallel hardware
//    Spark: parallel/distributed collections
// 2) Concurrent programming:
//    manage concurrent execution threads explicitly
//    Akka:  high-level abstraction with actors, message-passing, and futures (resolve races, locking, deadlocks)
//           serialize access to shared resources using queues and function passing
// The fundamental problem
//   concurrent threads access shared "mutable" state
// Imperative programming vs. Declarative programming (functional programming)
// 1) imperative thinking : time-wise thinking  (instatiate objects first used by later objects)
// 2) declarative thinking: space-wise thinking (building constructs upon small constructs)
