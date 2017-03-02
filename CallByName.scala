// 1) call-by-value:
//    compute the passed-in expression's value before calling function
//    so the same computed value is accessed every time inside the function, and the side effect happens only once
// 2) call-by-name:
//    recompute the passed-in expression's value every time it is accessed
object CallByName {
    def getX() = {
        println("side effect of calling getX()")
        1                                             // return value
    }

    def callByValue(x: Int) = {
        println("callByValue: x =" + x)
        println("callByValue: x =" + x)
    }

    def callByName(x: => Int) = {
        println("callByName: x =" + x)
        println("callByName: x =" + x)
    }

    def main(args: Array[String]) {
        println("1) callByValue(getX())")
        callByValue(getX())
        println("2) callByName(getX())")
        callByName(getX())
    }
}
