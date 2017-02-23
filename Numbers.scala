object Numbers {
    def main(args: Array[String]) {
        // numbers are objects
        println(1 + 2 * 3 / 4)
        println((1).+(((2).*(3))./(4)))
        // Range -> Vector -> List
        println(1.to(5))                          // construct Range(1, 2, 3, 4, 5)
        println(1.to(5).map((x) => x * x))        // mapped to Vector(1, 4, 9, 16, 25)
        println(1.to(5).map((x) => x * x).toList) // converted to List(1, 4, 9, 16, 25)
        // ParRange 
        println(1.to(5).par)                      // converted to ParRange(1, 2, 3, 4, 5)
        println(1.to(5).map(x => true -> 10L).toList)
    }
}
