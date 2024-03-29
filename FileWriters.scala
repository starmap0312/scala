import java.io.{File, PrintWriter, FileWriter, BufferedWriter, FileOutputStream}

object FileWriters {
    def main(args: Array[String]) {
        // 1) use PrintWriter: write() & append()
        val writer1 = new PrintWriter(new FileOutputStream(new File("printwriter.txt")))
        writer1.write("Hello, world\n")
        writer1.append("Hello, world\n")
        writer1.close
        // 2) use FileWriter
        val writer2 = new BufferedWriter(new FileWriter("filewriter.txt"))
        writer2.write("Hello, world\n")
        writer2.append("Hello, world\n")
        writer2.close()
    }
}
