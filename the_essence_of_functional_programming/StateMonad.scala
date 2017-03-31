// StateMonad
// 1) it is exactly like the Reader monad, except you can write as well as read
//
// State s a = State {
//   unapply() :: s -> (a, s)
// }
// apply(a) =
//   State(\state -> (a, state))
// m.flatMap(k) =
//   State(\state ->
//     {
//       let (a, state') = (unapply(m))(s)
//       (unapply( k(a) ))(state')
//     }
//   )
//
// Real example
//   greeter :: State[String, String]
//   greeter = do
//     name <- get                       // get the state
//     put "jack"                        // modify the state
//     return ("hello, " ++ name)
//   ex.
//     (unapply(greeter))("andy") => ("hello, andy", "jack")
//

object WriterMonad {

    def main(args: Array[String]) {
    }
}
