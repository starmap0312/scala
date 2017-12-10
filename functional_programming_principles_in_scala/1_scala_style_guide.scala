// 1) Avoid Casts and Type Tests
//    no runtime type-check and type-casting: i.e. isInstanceOf[] and asInstanceOf[]
//    let the compiler do the type-check and type-casting for you at compile-time
// 2) Indentation
//    in Scala, the standard is to indent using 2 spaces (no tabs) 
// 3) make Line Length short and Whitespace uniform
// ex1. good example: make the line length short
if (p(this.head)) {
  this.tail.filter0(p, accu.incl(this.head))
} else {
  this.tail.filter0(p, accu)
}
// ex2. good example: use local named variable (with meaningful names)
val newAccu = {
  if (p(this.head)) accu.incl(this.head)
  else accu
}
this.tail.filter0(p, newAccu)
// 4) choose meaningful Names for Methods and Values
//    ex. temp is not a good variable name
// 5) avoid unnecessary invocations of computation-intensive methods
// ex1. bad example: invokes findMin twice
this.remove(this.findMin).ascending(t + this.findMin)      // this invokes findMin twice
// ex2. good example: invokes findMin once
val min = this.findMin
this.remove(min).ascending(t + min)
// 6) Scala doesn’t require Semicolons
// ex. bad example: unnecessary semicolon
def filter(p: Tweet => Boolean): TweetSet = filter0(p, new Empty);
// 7) Don’t submit Code with "print" Statements
//    the final code should be free of debugging statements
// 8) Avoid using Return
// ex. bad example: the return can be dropped
def factorial(n: Int): Int = {
  if (n <= 0) return 1
  else return (n * factorial(n-1))
}
// 9) Avoid mutable local Variables
//    in a purely functional style, you should avoid side-effecting operations
// ex1. bad example: use mutable variables
def fib(n: Int): Int = {
  var a = 0
  var b = 1
  var i = 0
  while (i < n) {
    val prev_a = a
    a = b
    b = prev_a + b
    i = i + 1
  }
  a
}
// ex2. good example: no mutable variables
def fib(n: Int): Int = {
  def fibIter(i: Int, a: Int, b: Int): Int =
    if (i == n) a else fibIter(i + 1, b, a + b)
  fibIter(0, 0, 1)
}
// 10) Eliminate redundant "If" Expressions
// ex1. bad example: redundant if expression
if (cond) true else false
// ex2. good example: no redundant if expression
cond

