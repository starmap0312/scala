// Anonymous funciton
// Syntax
//   ([list of parameters]) => { function body }
// ex.
//   var inc = (x:Int) => x+1   // declare a anonymous function
//   var x = inc(7) - 1         // call a anonoymous function
// ex.
//   var mul = (x: Int, y: Int) => x * y
//   println(mul(3, 4))
// ex.
//   var userDir = () => { System.getProperty("user.dir") }
//   println(userDir)
//

object AnonymousFunciton {
    def oncePerSecond(callback: () => Unit) {
        while (true) { callback(); Thread sleep 1000 }
    }
    def main(args: Array[String]) {
        oncePerSecond(() => println("time flies like an arrow..."))
    }
}
