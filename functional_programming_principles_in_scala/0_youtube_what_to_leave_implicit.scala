// Reference
//   https://www.youtube.com/watch?v=Oij5V7LQJsA
// Implicit
// What is Context?
// 1) what comes with the text, but is not in the text
//    ex. the current "configuration"
//        the current scope
//        the meaning of < on this type (i.e. object's infix method)
//        the user on behalf of which the operation is performed
//        the security level in effect
// 2) traditional ways to express context (NOT GOOD DESIGN)
// 2.1) globals:
//      rigid if immutable
//      unsafe if mutable
// 2.2) monkey patching:
//      global effects implicitly 
// 2.3) dependency injection:
//      at run time (Spring, Gouice), or with macros (MacWire)
//      use class annotation, XMLs 
// 2.4) cake pattern
//      close coupling + recursion
// 3) the functional way (BETTER DESIGN)
//    parameterize all things: context is passed as an parameter to a function
// 3.1) why is it good?
//      no side effects
//      type safe (compile-time type check)
//      fine-grained control (you can pass and refine your context)
// 3.2) why is it not so good?
//      too many of parameters
//      repetitive: most of which hardly change
// solution: pass context as an implicit parameter
