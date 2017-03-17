// ReaderMonad (also called the Environment monad)
//   suppose you want to pass some env a lot of functions
//   the reader monad lets you pass a value to all the functions behind the scenes
//   it is good for passing (implicit) configuration/environment information through a computation
//   i.e. read values from a shared environment, pass values from function to function, and
//        execute sub-computations in a modified environment
//   ex. when you want to perform the same computation with different values (coinfiguration/environment)
//   ex. you need a database connection in every query function you execute
//   ex. configuration options read from a file that are needed across a number of functions
//   Reader is different because it's only field is a function
//     you can use runReader() to get that function
//     then you give this function some environment to get a value back (derived from the value encapsulated in Reader)
// in Monad terms,
// Reader(\env -> value)
//   env  : environment
//   value: a value (expression, function, etc.) you create from that environment
// ex.
//   reader = Reader(\env -> 5)
//     Reader[String, Int]: create a Reader that encapsulates a function that takes in a String and returns an Int
//     \env: String       : environment of the Reader
//     5: Int             : one can use unapply to get the Int value 5 out of the reader
//   unapply([Reader]):
//     unwraps the reader to get a function: (Reader(\env -> value)) -> (\env -> value)
//     unapply() takes in a Reader(\env -> value) and then an environment (env) and returns a value (value)
//     i.e. (unapply(reader))("some environment")  == 5
// Real Example:
// 1) reader: Reader[String, String]
//    reader = do
//      name <- ask                      ...... ask(): used to retrieve the environment
//      return ("hello, " ++ name)       ...... return a value, which is created for that environment
//    i.e.
//    reader = Reader(\env -> "hello" ++ env)
// 2) unapply:
//    (unapply(reader))("andy")             == "hello, andy"
//    (unapply(reader))("bob")              == "hello, bob"
// 3) flatMap(>>=): pipe reader1 to a function to get another reader
//    reader1 >>= func =
//    Reader(
//          \env -> (unapply(func(unapply(reader1))(env)))(env)
//    )
//    a) (unapply(reader1))      = (\env -> value1)
//    b) (unapply(reader1))(env) = value1
//    c) func(value1)            = reader2
//    d) (unapply(reader2))(env) = value2
//    reader >>= func = Reader(\env -> value2)
object ReaderMonad {

    def main(args: Array[String]) {
    }
}
