// what is monad?
// a way to build computer programs by joining simple components in robust ways, i.e. computation builder
// 1) it chains operations in some specific, useful way (like function composition) 
//    a monad is defined by:
//    return operator: create or generate values              (ex. generator/stream)
//    bind operator: link action or execution steps together  (ex. function composition, g compose f, or f andThen g)
// 2) bind function: (In Haskell, it's named >>=)
//    either write calls to the bind operator, or use syntax sugar asking compiler insert function calls for you
//    (either way, each step is separated by a call to this bind function)
// 3) bind function's job is to take the output from the previous step, and feed it into the next step (like pipeline)
// 4) in asynchronous applications: we prefer a declarative, reactive API over "callback hell"
//
// definition of a monad:
// 1) a type constructor T 
// 2) operation identity:
//    unit(): in Scala
//    return: in Haskell
//    unary return operation takes a value from a plain type (x)
//    puts it into a container using the constructor T, creating a monadic value (with type T x)
// 3) operation bind:
//    flatMap(): in Scala
//    >>=      : in Haskell
//    binary bind operation takes two arguments:
//      a monadic value with type T x 
//      a function (x -> T y) that can transform the value
//    it unwraps x and feeds it into function (x -> T y), creating a new monadic value, that is fed into the next bind operator
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
// 1) List comprehension:
//    [x * 2 | x <- (1 to 10) && odd x]  ==> returns the doubles of all odd numbers in the range from 1 to 10
//    (a syntactic sugar of)
//    for (x <- 1 to 10) {
//        if (odd x) {
//            yield [x * 2];
//        else {
//            yield []
//        }
//    }
//    the operations are chained such that if an operation returns a list, then the following operations are performed
//      on every item in the list (like list.map([function]))
// 2) I/O:
//    while(true) {
//        println("What is your name?")
//        name <- getLine
//        println("Welcome, " + name + "!")
//    }
//    it performs the operations sequentially, but passes a "hidden variable" along, which represents "the state of the world"
//      this allows us to write I/O code in a pure functional manner
