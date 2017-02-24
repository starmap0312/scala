// syntax:
// def future[T](body: =>T)(implicit execctx: ExecutionContext): Future[T] = Future[T](body)

import concurrent.Future
import concurrent.ExecutionContext.Implicits.global
import concurrent.Promise
import scala.util.{Success, Failure}
import scala.concurrent.Await
import scala.concurrent.duration._

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
