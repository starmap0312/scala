// functional programming:
// 1) pure function:
//    a function should always return the same value when called with same parameters
// 2) no side effect
//    ex. no disk I/O or changes to external variables/resources
// OOP: 
// 1) NOT pure function:
//    a function may return different values when called with same parameters
//    ex. a function that returns the current temperature, it reads a temperature sensor (a external mutable resource) 
// 2) many side effects
//    ex. output to a file, modify external variables or resources, etc.
//
// characteristics of function programming
// 1) higher-order functions
//    first class functions: allows nesting one function as a parameter of another
//    ex.
//      def double(x)
//      double(double(2)) == double(4)
// 2) late evaluation 
