// Supervision:
// 1) the supervisor delegates tasks to subordinates and therefore must respond to their failures
// 2) when a subordinate . throws an exception, it suspends itself and all its subordinates and sends a message to its supervisor, signaling failure
// 3) Supervision options:
//    i) Resume the subordinate, keeping its accumulated internal state
//       resuming an actor resumes all its subordinates
//   ii) Restart the subordinate, clearing out its accumulated internal state
//       restarting an actor entails restarting all its subordinates
//  iii) Stop the subordinate permanently
//       terminating an actor will also terminate all its subordinates
//   iv) Escalate the failure, thereby failing itself

// Restart process:
// 1) suspend the actor (i.e. not processing any messages until resumed), and recursively suspend all children
// 2) call the old instance’s preRestart hook (default: sending termination requests to all children and calling postStop)
// 3) wait for all children to terminate (using context.stop()) during preRestart to actually terminate
//    this is non-blocking, the termination notice from the last killed child will effect the progression to the next step
// 4) create new actor instance by invoking the factory again
// 5) invoke postRestart on the new instance (default: calls preStart)
// 6) send restart request to all children which were not killed in step 3
//    restarted children will follow the same process recursively, from step 2
// 7) resume the actor

// there are 2 classes of supervision strategies in akka
// they are configured with a mapping from exception type to supervision directive (ex. Resume/Restart/Stop/Escalate)
// 1) One-For-One Strategy (default)
//    it applies the obtained directive only to the failed child
// 2) All-For-One Strategy
//    it applies the obtained directive to all siblings 
//    i.e. a failure of one child affects the function of the others (they are inextricably linked)

// Fault Tolerance
// 0) Default Supervisor Strategy
      case _: ActorInitializationException => Stop     // ActorInitializationException will stop the failing child actor
      case _: ActorKilledException         => Stop     // ActorKilledException         will stop the failing child actor
      case _: DeathPactException           => Stop     // DeathPactException           will stop the failing child actor
      case _: Exception                    => Restart  // Exception                    will restart the failing child actor
      case _:                              => Escalate // Other Throwable              will be escalated to parent actor

// 1) Customize a Supervisor Strategy in a supervisor Actor class
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._

override val supervisorStrategy =
  OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) { // the strategy at most restarts a child Actor up to 10 times in 1 minute
    // ex.
    //   (maxNrOfRetries=-1, withinTimeRange=Duration.inf): the child is always restarted without any limit
    //   (maxNrOfRetries=-1, withinTimeRange=1 minute)    : maxNrOfRetries is treated as 1
    case _: ArithmeticException      => Resume
    case _: NullPointerException     => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception                => Escalate
  }

