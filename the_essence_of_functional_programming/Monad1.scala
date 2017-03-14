// what is monad?
// a way to build computer programs by joining simple components in robust ways, i.e. computation builder
// 1) it chains operations in some specific, useful way (like function composition) 
//    a monad is defined by:
//    apply()  : return operator, create/wrap values                 (ex. generator/stream)
//    flatMap(): bind operator, link action/execution steps together (ex. function composition, g compose f, or f andThen g)
// 2) flatMap() function: (In Haskell, it's named >>=)
//      either write calls to the flatMap operator, or use syntax sugar asking compiler insert function calls for you
//      (either way, each step is separated by a call to this bind function)
//    flatMap() function's job is to take the output from previous step, and feed it into the next step (like pipeline)
// 3) in asynchronous applications, we prefer a declarative, reactive API over "callback hell"
//
// definition of a monad:
// 1) a type constructor M 
// 2) apply()/identity operation:
//    apply()/unit: in Scala
//    return      : in Haskell
//    unary return operation takes a value from a plain type (x)
//    puts it into a container using the constructor M, creating a monadic value (with type M x)
// 3) flatMap()/bind operation:
//    flatMap(): in Scala
//    >>=      : in Haskell
//    flatMap()/binary bind operation takes two arguments:
//      a monadic value with type M x 
//      a function (x -> M y) that can transform the value
//    it unwraps x and feeds it into function (x -> M y), creating a new monadic value
//      which can be fed into the next bind operator
//
// the monad laws:
//    it provides two functions which conform to three laws
// 1) place a value into monadic context
//    Scala's Option: Some()
//    Java 8's Optional: Optional.of()
//    a type of variables which may have a null value, so functions unable to handle null values are explicitly notified
//    used for exception handling or defining partial function
// 2) apply a function in monadic context
//    Scala's Option: flatMap()
//    Java 8's Optional: flatMap()
//
// examples:
// 1) List Monad: (List comprehension)
//    create a List of the doubles of all odd numbers in the range from 1 to 4
//      List(1, 2, ..., 4).filter(odd).flatMap(x => x * 2)
//      (can be written as)
//      [x * 2 | x <- (1 to 4) && odd x]  ==> returns the doubles of all odd numbers in the range from 1 to 4
//      (is a syntactic sugar of)
//      for (x <- 1 to 4) {
//          if (odd x) {
//              yield [x * 2];
//          else {
//              yield []
//          }
//      }
//    List(1, 2, 3, 4) => List(List(1), List(), List(3), List()) => List(1, 3) => List(2, 6)
//    the operations are chained such that if an operation returns a list, then the following operations are performed
//      on every item in the list (like list.map([function]))
// 2) I/O Monad:
//    while(true) {
//        name <- getLine
//        println("Welcome, " + name + "!")
//    }
//    it performs the operations sequentially, passing a "hidden variable" along, which represents "state of the world"
//    this allows us to write I/O code in a pure functional manner
