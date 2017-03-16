// ReaderMonad (also called the Environment monad)
//   suppose you want to pass some env a lot of functions
//   the reader monad lets you pass a value to all the functions behind the scenes
//   it is good for passing (implicit) configuration/environment information through a computation
//   i.e. read values from a shared environment, pass values from function to function, and
//        execute sub-computations in a modified environment
//   ex. when you want to perform the same computation with different values (coinfiguration/environment)
//   ex. you need a database connection in every query function you execute
//   ex. configuration options read from a file that are needed across a number of functions
// Reader env a
//   env: environment
//   a: a value (or function) you create from that environment
// ex.
//   reader = Monad.apply(5) :: Reader String Int    ...... create a Reader that takes in a String and returns an Int
//   String: environment of the Reader
//   Int   : one can use runReader to get the Int value out of the reader
// runReader: (Reader env a) -> env -> a
//   runReader takes in a (Reader env a) and an environment (env) and returns a value (a)
// ex.
//   (runReader reader) "some environment"  == 5
// Real Example:
// 1) reader: Reader String String
//    reader = do
//      name <- ask                      ...... ask: used to retrieve the environment
//      return ("hello, " ++ name)       ...... a value created for the environment
// 2) runReader:
//    (runReader reader) "andy"             == "hello, andy"
//    (runReader reader) "bob"              == "hello, bob"
// 3) >>=:
//    reader >>= func =
//      Reader (\env -> runReader (func (runReader reader env)) env
object ReaderMonad {

    def main(args: Array[String]) {
    }
}
