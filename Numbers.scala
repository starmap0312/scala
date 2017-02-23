object Numbers {
    def main(args: Array[String]) {
        // numbers are objects
        println(1 + 2 * 3 / 4)
        println((1).+(((2).*(3))./(4)))
        // 
        println(1.to(5))     // Range(1, 2, 3, 4, 5)
        println(
            1.to(5).map(     // mapped to Vector(1, 4, 9, 16, 25)
                (x) => x * x
            )
        )
    }
}
