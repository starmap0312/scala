import com.typesafe.config.ConfigFactory

object TypeSafeConfig {
    def main(args: Array[String]) {
        val config = ConfigFactory.load("example.conf")
        val value = config.getString("my.secret.value")
        println(s"My secret value is $value")
    }
}
