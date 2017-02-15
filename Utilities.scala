import scala.io.{Source, StdIn}

object Utilities {
    def main(args: Array[String]) {
        // 0) StdIn.readLine(): read line from STDIN
        println(StdIn.readLine())

        // 1) Source.fromFile([filename]): read line from file
        // 1.1) use getLines() to fetch lines
        for (line <- Source.fromFile("data.txt").getLines()) {
            println(line)
        }
        // 1.2) print out the file content
        Source.fromFile("data.txt").foreach(print)

        // 2) Source.fromURL([url]): read from URL
        val html = Source.fromURL("http://www.example.com").mkString
        println(html)
    }
}
