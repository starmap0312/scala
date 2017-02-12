// functional programming:
// 1) pure function:
//    a function should always return the same value when called with same parameters
// 2) no side effect
//    ex. no disk I/O or changes to external variables/resources
// imperative programming: 
// 1) NOT pure function:
//    a function may return different values when called with same parameters
//    ex. a function that returns the current temperature, it reads a temperature sensor (a external mutable resource) 
// 2) many side effects
//    ex. output to a file, modify external variables or resources, etc.
//
// (example: javascript, imperative programming)
function guess(x) {
    if (x == 7) {
        return "7 is guessed"
    } else {
        return "try again"
    }
}
// (example: Haskell, functional programming)
guess :: Int -> [Char]                        // type annotation
guess 7 = "7 is guessed"                      // Pattern Matching feature: matching over a pattern
guess x = "try again"

// (example: Haskell, a function that receives a List of Ints and adds 1 to each element of it)
plus1 :: [Int] -> [Int]
plus1 []      = []                            // if it matches an empty List [], then returns another empty List
plus1 (x:xs)  = x + 1 : plus1 xs              // if it matches a non-empty List, then
                                              //   name the first element of the List as x and the rest of the List as xs 
                                              //   perform the sum and concatenates with a recursive call
// (example: python, imperative programming) 
def plus1(x):
    if x == []:
        return []
    return [x[0] + 1] + plus1(x[1:])

// why is it good?
//   it becomes powerful when implementing more complex data structure
//   the code contains only 2 lines and is readable

// characteristics of functional programming
// 1) first class functions
//
// (example: javascript)
var add = function(a, b) { // storing an anonymous function to variable add
    return a + b
}

// 2) higher-order functions
//    allowing nesting one function as a parameter of another
//
// (example: javascript)
document
    .querySelector("#button")
    .addEventListener(
        "click",
        function() {
            alert("yay i got clicked")
        }
    ) 

// 3) pure functions
//    a function always return the same value no matter the environment, threads, or any evaluation order
//    it does NOT cause any side-effects in other parts of the program
// 4) closures
//    you can save some data inside a function that's only accessible to a specific returning function
//    i.e the returning function keeps its execution environment
//
// (example: javascript)
var add = function(a) {  // variable a was enclosed and is only accessible to the returning function
    return function(b) {
        return a + b
    }
}
var add2 = add(2)
add2(3)                  // this returns 5 

// 5) immutable state
//    you can't change any state at all
//
// (example: OCaml)
let x = 5;;              // x will be forever 5 (you can use x and 5 interchangeably)
x = 6;;
print_int x;;            // this prints 5

// advantages of functional programming
// 1) good for complex mathematical modelling
// 2) good for parallel processing
//    pure functions always returning the same value regardless of the order they are run
//    i.e. no race conditions

// late evaluation 
//
