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
// 3) apply([function]):
//    Reader([function])
//    wrap function inside a Reader
// 3) flatMap(>>=): pipe reader1 to a function to get another reader
//    reader1.flatMap(func) =
//      apply(
//        \env -> (
//          unapply(
//            func(
//              (unapply(reader1))(env)        ...... unwrap reader1 which will take env to produce value1
//            )                                ...... func then takes value1 to produce reader2
//          )(env)                             ...... unwrap reader2 which will take env to produce value2
//        )
//      )                                      ...... wrap funciton \env -> value2 to produce reader3
// 3.1) (unapply(reader1))      = (\env -> value1)
// 3.2) (unapply(reader1))(env) = value1
// 3.3) func(value1)            = reader2
// 3.4) (unapply(reader2))(env) = value2
// 3.5) reader1.flatMap(func)   = Reader(\env -> value2) = reader3
//   we can then unwrap reader3 to get function \env -> value2,
//     by passing some environment to that function, the environment in effect passes through
//     both reader1's wrapped function as well as func's wrapped function 
// Composition of Reader Monad
//   for any functions f, g that takes a value and returns a Reader
//   we can compose them as such:
//     (g <=< f)("some environment") = f("some environment").flatMap(g)
object ReaderMonad {

    def main(args: Array[String]) {

        object Reader {
            def apply[T, R](x: T => R): Reader[T, R] =
        }
        class Reader[T, R] {
        }
        
    }
}
