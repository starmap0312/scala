import scala.io.{Source}

object Utilities {
    def main(args: Array[String]) {
        // 1) read line from file
        // 1.1)
        for (line <- Source.fromFile("data.txt").getLines()) {
            println(line)
        }
        // 1.2)
        Source.fromFile("data.txt").foreach(print)

        // 2) read from URL
        val html = Source.fromURL("http://www.example.com").mkString
        println(html)
    }
}
