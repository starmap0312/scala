// Future vs. Promise:
// 1) a future is a placeholder object for a value that may be become available at some point in time, asynchronously
//    it is not the asynchronous computation itself
//    there is a future constructor called future that returns such a placeholder object and spawns an asynchronous computation that completes this placeholder object
// 2) A Promise is like a "writable-once" container that can be used to complete a Future with the written value
// 
// dividing Promise and Future into 2 separate interfaces was a design decision:
// 1) we don not want to allow clients of futures to complete them, as there could be any number of completers
// 2) the idea is to have a future client that subscribes to the data to act on it once the data arrives
//    the role of the promise client is that it can actively provide that data
//    i.e. Promise -> (data) -> Future -> (data)
//    (note: mixing these two roles can lead to programs that are harder to understand)
// 3) why the Promise trait does not extend Future?
//    this is decision to discourage programmers from passing Promises to clients who upcast the Promise to Future 
//    instead, you need to explicitly call promise.future() every time
//    if you return a Promise, you are giving the right to complete it to somebody else (by returning the Future you are giving the right to subscribe to it)

// syntax:
// def future[T](body: =>T)(implicit execctx: ExecutionContext): Future[T] = Future[T](body)

import scala.concurrent.Future
import scala.concurrent.Promise
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object FuturePromise {
    def task(): Future[String] = {
        val promise = Promise[String]()
        val producer = Future {
            println("Starting a one-seconds task")
            Thread.sleep(1000)
            promise.success("task completed")           // promise.failure(Exception("task failed")))
            println("Ending a one-seconds task")
        }
        promise.future
    }

    def main(args: Array[String]) {
        // 1) construct a Future
        val future: Future[String] = Future { "a future task" }

        // 2) construct a Promise
        val promise1 = Promise[String]()           // method 1: give the type as a type parameter to the factory method
        val promise2: Promise[String] = Promise()  // method 2: give the compiler a hint by specifying the type of val 

        // 3) success(): explicitly complete a future successfully (cannot write result to the future anymore)
        val taskFuture: Future[String] = task()
        // 4) onComplete(): do something based on the result of promise.future
        taskFuture.onComplete(
            {
                case Success(message) => println("onComplete: Success")
                case Failure(ex)      => println("onComplete: Failure")
            }
        )
        println(Await.result(taskFuture, 10.seconds))
    }
}
