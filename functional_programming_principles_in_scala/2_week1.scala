// Programming Paradigms
1) imperative programming
   variables assignments, control structures (if-else branch, loops, etc.)
2) functional programming (declaritive)
   no mutations, operators/functions
   in a restricted sense: no mutable variables, assignments, control structures
     ex. Haskell without I/O Monad or UnsafePerformIO
   in a wider sense     : contruct programs using functions
     ex. Haskell in full , or Scala
     functions are first-class citizens, i.e. they can be passed as parameters or as returned results
     there are a set of operators to compose functions
   pros:
     simple reasoning principles, i.e. good modularity
     good for parallelism, i.e. for multicores (ex. akka) and for distributed systems (ex. spark)
(orthogonal to OOP, both of the above two can be combined with OOP)
3) object-oriented programming

// Elements of Programming
1) side effect
   ex. i++
       it has side effect, as variable i is incremented by one (mutated)
2) does every expression evaluate to a value in a finite number of steps? No.
   ex. 
   def loop: Int = loop // (loop -> loop -> ... ...)
3) evaluation strategies: there are more than one way to evaluate an expression
   call-by-value vs. call-by-name (lazy evaluation)
   ex.
   (call-by-value: it evaluates function argument only once)
     sumOfSquare(3, 2 + 2) =  sumOfSquare(3, 4) = square(3) + square(4) = (3 * 3) + square(4) = 9 + square(4)
       = 9 + (4 * 4) = 9 + 16 = 25
   (call-by-name: a function argument is not evaluated if it is not used in the function body)
     sumOfSquare(3, 2 + 2) =  square(3) + square(2 + 2) = (3 * 3) + square(2 + 2) = 9 + square(2 + 2)
       = 9 + (2 + 2) * (2 + 2) = 9 + 4 * (2 + 2) = 9 + 4 * 4 = 9 + 16 = 25
4) theorem of lamda-calculus:
   both strategies evaluate to the same final value as long as they consists of pure functions (no side effect) and both terminate

// Evaluation Strategies and Terminations
1) if call-by-value terminates, then call-by-name terminates too, however,
   if call-by-name terminates, then it does not imply call-by-value terminates
   ex.
     def first(x: Int, y: Int) = x
     first(1, loop) // call-by-value will not terminate, as evaluating loop does not terminate (call-by-name does not evaluate loop)

// Conditionals and Value Definitions
1) conditional expression in scala
   ex.
     def abs(x: Int) = if (x >= 0) x else -x
2) def form is by-name
   val form is by-value

// Example
1) a recursive method must have an explicit return type (otherwise, the compiler cannot infer its type)
   ex.
     def sqrtIter(guess: Double, x: Doulbe): Double = // return type must be explcit for recursive methods
       if (isGoodEnough(guess, x)) guess
       else sqrtIter(improve(guess, x), x)
