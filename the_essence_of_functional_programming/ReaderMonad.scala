// ReaderMonad (also called the Environment monad)
//   suppose you want to pass some env a lot of functions
//   the reader monad lets you pass a value to all the functions behind the scenes
//   it is good for passing (implicit) configuration/environment through a computation
//   ex. when you need to read values from a shared environment, pass values from function to function, and
//       execute sub-computations in a modified environment
//   ex. when you need to perform the same computation with different values (coinfiguration/environment)
//   ex. when you need a database connection in every query function you execute
//   ex. when some configuration options read from a file are needed across a number of functions
//   Reader is different because it's only field is a function
//     you can use unapply() to get that function
//     then you give this function some environment to get a value back (derived from the value encapsulated in Reader)
// Reader(\env -> value)
//   env  : environment
//   value: a value (expression, function, etc.) you create from that environment
// ex.
//   reader = Reader(\env -> 5)
//     Reader[String, Int]: create a Reader that encapsulates a function that takes in a String and returns an Int
//     \env: String       : environment of the Reader
//     5: Int             : one can use unapply to get the Int value 5 out of the reader
//   unapply([reader]):
//     it unwraps the reader to get a function: (Reader(\env -> value)) -> (\env -> value)
//     it takes in a Reader(\env -> value) and then an environment (env) and returns a value (value)
//     i.e. (unapply(reader))("some environment")  == 5
//   apply(a):
//     Reader(\_ -> a)
//     ex. apply(2) = Reader(\_ -> 2)
//   ask():
//     Reader(\x -> x)
// Real Example:
// 1) reader: Reader[String, String]
//    reader = do
//      name <- ask                      ...... ask(): used to retrieve the environment
//      return ("hello, " ++ name)       ...... return a value, which is created for that environment
//    i.e.
//    reader = Reader(\env -> "hello" ++ env)
// 2) unapply([reader]):
//    unwrap the reader, obtaining a function that takes an environment and returns a value based on that environment
//    (unapply(reader))("andy")             == "hello, andy"
//    (unapply(reader))("bob")              == "hello, bob"
// 3) flatMap(>>=): pipe reader1 to a function to get another reader
//    reader1.flatMap(func) =
//      apply(
//        \env -> (
//          unapply(
//            func((unapply(reader1))(env))    ...... = func(value1)            = reader2
//          )
//        )(env)                               ...... = (unapply(reader2))(env) = value2
//      )                                      ...... = apply(\env -> value2)   = reader3
// 3.1) (unapply(reader1))      = (\env -> value1)
// 3.2) (unapply(reader1))(env) = value1
// 3.3) func(value1)            = reader2
// 3.4) (unapply(reader2))(env) = value2
// 3.5) reader1.flatMap(func)   = Reader(\env -> value2) = reader3
// Reader Composition
//   for any functions f, g that takes a value and returns a Reader
//   if you compose them as such:
//     (g <=< f)("some environment")                 = f("some environment").flatMap(g)
//   the environement will be passed on to function g 
//     unapply(f("some environment")) = \env -> value1 (based on "some environment")
//     g(value1)                      = Reader(\env -> value2)
//     (g <=< f)("some environment")
//     = f("some environment").flatMap(g)
//     = apply(
//         \env -> (
//           unapply(
//             g( (unapply(f("some environment")))(env) )
//           )
//         )(env)
//       )     
object ReaderMonad {

    def main(args: Array[String]) {
    }
}
